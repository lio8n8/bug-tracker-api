package com.app.bugtracker.tasks

import com.app.bugtracker.BaseControllerIntegrationTest
import com.app.bugtracker.auth.services.ITokensService
import com.app.bugtracker.projects.services.IProjectsService
import com.app.bugtracker.tasks.dto.TaskDTO
import com.app.bugtracker.tasks.dto.UserTaskRequestDTO
import com.app.bugtracker.tasks.models.Priority
import com.app.bugtracker.tasks.models.Status
import com.app.bugtracker.tasks.models.Type
import com.app.bugtracker.tasks.services.ITasksService
import com.app.bugtracker.users.models.User
import com.app.bugtracker.users.services.IUsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.reactive.function.BodyInserters

import java.time.LocalDateTime

import static com.app.bugtracker.Urls.TASK
import static com.app.bugtracker.Urls.TASKS
import static com.app.bugtracker.Urls.TASK_ASSIGNEE
import static com.app.bugtracker.Urls.TASK_ASSIGNEES
import static com.app.bugtracker.Urls.TASK_PRIORITIES
import static com.app.bugtracker.Urls.TASK_STATUSES
import static com.app.bugtracker.Urls.TASK_TYPES
import static com.app.bugtracker.Utils.authenticate
import static com.app.bugtracker.Utils.getCreateProjectRequest
import static com.app.bugtracker.Utils.getCreateTaskRequest
import static com.app.bugtracker.Utils.getCreateUserRequest
import static org.springframework.http.HttpHeaders.AUTHORIZATION
import static org.springframework.http.MediaType.APPLICATION_JSON

/**
 * Integration test for Tasks controller.
 */
class TasksControllerIntegrationTest extends BaseControllerIntegrationTest{

    @Autowired
    IUsersService usersService

    @Autowired
    ITokensService tokensService

    @Autowired
    ITasksService tasksService

    @Autowired
    IProjectsService projectsService

    private User user

    def setup() {
        user = usersService.create(getCreateUserRequest())
        authenticate(user)
    }

    def 'find all tasks'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'task exists'
        def task = tasksService.create(getCreateTaskRequest().tap {
            projectId = project.id
        })

        when: 'find all tasks'
        webTestClient.get()
                .uri(TASKS, PageRequest.of(0, 25))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()

        then: 'success'
        true
    }

    def 'find task by id'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'task exists'
        def task = tasksService.create(getCreateTaskRequest().tap {
            projectId = project.id
        })

        when: 'find task by id'
        webTestClient.get()
                .uri(TASK, task.id)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(TaskDTO.class)
                .consumeWith({ t ->
                    assert t.responseBody.id == task.id
                    assert t.responseBody.title == task.title
                    assert t.responseBody.description == task.description
                    assert t.responseBody.type == task.type
                    assert t.responseBody.priority == task.priority
                    assert t.responseBody.status == task.status
                    assert t.responseBody.createdBy.id == user.id
                    assert t.responseBody.updatedBy.id == user.id
                    assert t.responseBody.createdAt
                    assert t.responseBody.updatedAt
                })

        then: 'success'
        true
    }

    def 'find task types'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'task types'
        def types = Type.values()

        when: 'find task types'
        webTestClient.get()
                .uri(TASK_TYPES)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Type)
                .consumeWith({ t ->
                    assert t.responseBody.containsAll(types)
                })

        then: 'success'
        true
    }

    def 'find task priorities'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'task priorities'
        def priorities = Priority.values()

        when: 'find task priorities'
        webTestClient.get()
                .uri(TASK_PRIORITIES)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Priority)
                .consumeWith({ p ->
                    assert p.responseBody.containsAll(priorities)
                })

        then: 'success'
        true
    }

    def 'find task statuses'() {
        given: 'token'
        def token = tokensService.createToken(user.username)
        and: 'task statuses'
        def statuses = Status.values()

        when: 'find task statuses'
        webTestClient.get()
                .uri(TASK_STATUSES)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Status)
                .consumeWith({ s ->
                    assert s.responseBody.containsAll(statuses)
                })

        then: 'success'
        true
    }

    def 'create task'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'create task request'
        def request = getCreateTaskRequest().tap {
            projectId = project.id
        }

        when: 'create task'
        webTestClient.post()
                .uri(TASKS)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(TaskDTO)
                .consumeWith({ t ->
                    assert t.responseBody.id
                    assert t.responseBody.title == request.title
                    assert t.responseBody.description == request.description
                    assert t.responseBody.type == request.type
                    assert t.responseBody.priority == request.priority
                    assert t.responseBody.status == request.status
                    assert t.responseBody.createdBy.id == user.id
                    assert t.responseBody.updatedBy.id == user.id
                    assert t.responseBody.createdAt.isBefore(LocalDateTime.now())
                    assert t.responseBody.updatedAt.isBefore(LocalDateTime.now())
                })

        then: 'success'
        true
    }

    def 'update task'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'task exists'
        def task = tasksService.create(getCreateTaskRequest().tap {
            projectId = project.id
        })

        and: 'update task request'
        def request = getCreateTaskRequest().tap {
            projectId = project.id
        }

        when: 'update task'
        webTestClient.put()
                .uri(TASK, task.id)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(TaskDTO.class)
                .consumeWith({ t ->
                    assert t.responseBody.id == task.id
                    assert t.responseBody.title == request.title
                    assert t.responseBody.description == request.description
                    assert t.responseBody.type == request.type
                    assert t.responseBody.priority == request.priority
                    assert t.responseBody.status == request.status
                    assert t.responseBody.updatedBy.id == user.id
                })

        then: 'success'
        true
    }

    def 'patch task'() {
        // TODO: Implement!
    }

    def 'delete task by id'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'task exists'
        def task = tasksService.create(getCreateTaskRequest().tap {
            projectId = project.id
        })

        when: 'delete task'
        webTestClient.delete()
                .uri(TASK, task.id)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isNoContent()

        then: 'success'
        true
    }

    def 'assign task to user'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'task exists'
        def task = tasksService.create(getCreateTaskRequest().tap {
            projectId = project.id
        })

        and: 'request'
        def request = UserTaskRequestDTO.builder()
                .userId(user.id)
                .build()

        when: 'add assignee to task'
        webTestClient.post()
                .uri(TASK_ASSIGNEES, task.id)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(TaskDTO.class)
                .consumeWith({ t ->
                    assert t.responseBody.id == task.id
                    assert t.responseBody.assignee.id == user.id
                })

        then: 'success'
        true
    }

    def 'change task assignee'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'task exists'
        def task = tasksService.create(getCreateTaskRequest().tap {
            projectId = project.id
        })

        and: 'task has assignee'
        tasksService.assignTaskToUser(task.id, UserTaskRequestDTO.builder()
                .userId(user.id)
                .build())

        and: 'a new assignee'
        def assignee = usersService.create(getCreateUserRequest())

        and: 'request'
        def request = UserTaskRequestDTO.builder()
                .userId(assignee.id)
                .build()

        when: 'change task assignee'
        webTestClient.put()
                .uri(TASK_ASSIGNEE, task.id, user.id)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(TaskDTO.class)
                .consumeWith({ t ->
                    assert t.responseBody.id == task.id
                    assert t.responseBody.assignee.id == assignee.id
                })

        then: 'success'
        true
    }

    def 'delete task assignee'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'task exists'
        def task = tasksService.create(getCreateTaskRequest().tap {
            projectId = project.id
        })

        and: 'task has assignee'
        tasksService.assignTaskToUser(task.id, UserTaskRequestDTO.builder()
                .userId(user.id)
                .build())

        when: 'delete task assignee'
        webTestClient.delete()
                .uri(TASK_ASSIGNEE, task.id, user.id)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(TaskDTO.class)
                .consumeWith({ t ->
                    assert t.responseBody.id == task.id
                    assert t.responseBody.assignee == null
                })

        then: 'success'
        true
    }
}
