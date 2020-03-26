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

import java.time.Instant

import static com.app.bugtracker.Urls.TASK
import static com.app.bugtracker.Urls.TASKS
import static com.app.bugtracker.Urls.TASKS_BY_ASSIGNEE
import static com.app.bugtracker.Urls.TASKS_FOR_CURRENT_USER
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

        expect: 'find all tasks'
        webTestClient.get()
                .uri(TASKS, PageRequest.of(0, 25))
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
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

        expect: 'find task by id'
        webTestClient.get()
                .uri(TASK, task.id)
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
    }

    def 'find task types'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'task types'
        def types = Type.values()

        expect: 'find task types'
        webTestClient.get()
                .uri(TASK_TYPES)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Type)
                .consumeWith({ t ->
                    assert t.responseBody.containsAll(types)
                })
    }

    def 'find task priorities'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'task priorities'
        def priorities = Priority.values()

        expect: 'find task priorities'
        webTestClient.get()
                .uri(TASK_PRIORITIES)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Priority)
                .consumeWith({ p ->
                    assert p.responseBody.containsAll(priorities)
                })
    }

    def 'find task statuses'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'task statuses'
        def statuses = Status.values()

        expect: 'find task statuses'
        webTestClient.get()
                .uri(TASK_STATUSES)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Status)
                .consumeWith({ s ->
                    assert s.responseBody.containsAll(statuses)
                })
    }

    def 'find tasks by assignee id'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'assignee'
        def assignee = usersService.create(getCreateUserRequest())

        and: 'tasks'
        def size = 5
        (1..size).each {
            def task = tasksService.create(getCreateTaskRequest().tap {
                projectId = project.id
            })
            tasksService.assignTaskToUser(task.id, UserTaskRequestDTO.builder()
                    .userId(assignee.id)
                    .build())
        }

        expect: 'find all tasks'
        webTestClient.get()
                .uri(TASKS_BY_ASSIGNEE, assignee.id, PageRequest.of(0, 25))
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
    }

    def 'find task assigned to current user'() {
        given: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'assignee'
        def assignee = usersService.create(getCreateUserRequest())

        and: 'tasks'
        def size = 5
        (1..size).each {
            def task = tasksService.create(getCreateTaskRequest().tap {
                projectId = project.id
            })
            tasksService.assignTaskToUser(task.id, UserTaskRequestDTO.builder()
                    .userId(assignee.id)
                    .build())
        }

        and: 'token'
        def token = tokensService.createToken(assignee.username)

        expect: 'find all tasks'
        webTestClient.get()
                .uri(TASKS_FOR_CURRENT_USER, PageRequest.of(0, 25))
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
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

        expect: 'create task'
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
                    assert t.responseBody.createdAt.isBefore(Instant.now())
                    assert t.responseBody.updatedAt.isBefore(Instant.now())
                })
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

        expect: 'update task'
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

        expect: 'delete task'
        webTestClient.delete()
                .uri(TASK, task.id)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isNoContent()
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

        expect: 'add assignee to task'
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

        expect: 'change task assignee'
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

        expect: 'delete task assignee'
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
    }
}
