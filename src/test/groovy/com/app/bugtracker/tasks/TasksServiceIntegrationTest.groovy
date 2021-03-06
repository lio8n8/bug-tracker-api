package com.app.bugtracker.tasks

import com.app.bugtracker.BaseServiceIntegrationTest
import com.app.bugtracker.projects.services.IProjectsService
import com.app.bugtracker.tasks.dto.UserTaskRequestDTO
import com.app.bugtracker.tasks.models.Status
import com.app.bugtracker.tasks.repositories.ITasksRepository
import com.app.bugtracker.tasks.services.ITasksService
import com.app.bugtracker.users.models.User
import com.app.bugtracker.users.services.IUsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest

import java.time.Instant

import static com.app.bugtracker.Utils.authenticate
import static com.app.bugtracker.Utils.getCreateProjectRequest
import static com.app.bugtracker.Utils.getCreateTaskRequest
import static com.app.bugtracker.Utils.getCreateUserRequest

/**
 * Integration tests for Tasks service.
 */
class TasksServiceIntegrationTest extends BaseServiceIntegrationTest {

    @Autowired
    ITasksService tasksService

    @Autowired
    IUsersService usersService

    @Autowired
    ITasksRepository tasksRepository

    @Autowired
    IProjectsService projectsService

    private User user

    def setup() {
        user = usersService.create(getCreateUserRequest())
        authenticate(user)
    }

    def 'find all tasks'() {
        given: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'task created'
        def task = tasksService.create(getCreateTaskRequest().tap {
            projectId = project.id
        })

        when: 'find all tasks'
        def res = tasksService.findAll(PageRequest.of(0, 25))

        then: 'page with task returned'
        assert res.getContent().any { it.id == task.id}
    }

    def 'find tasks by id'() {
        given: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'task created'
        def task = tasksService.create(getCreateTaskRequest().tap {
            projectId = project.id
        })

        when: 'find all tasks'
        def res = tasksService.findById(task.id)

        then: 'task returned'
        with(res) {
            id == task.id
            title == task.title
            description == task.description
            status == task.status
            priority == task.priority
            type == task.type
            project.id == task.project.id
        }
    }

    def 'find tasks by assignee id'() {
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

        when: 'find tasks by assignee id'
        def res = tasksService.findByAssigneeId(assignee.id, PageRequest.of(0, 25))

        then: 'tasks list size are correct'
        res.content.size() == size

        and: 'all task contain created assignee'
        assert res.content.every { t -> t.assignee.id == assignee.id }
    }

    def 'find tasks assigned for current user'() {
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

        and: 'authenticate assignee'
        authenticate(assignee)

        when: 'find tasks for current users'
        def res = tasksService.findByCurrentUser()

        then: 'tasks list size are correct'
        res.content.size() == size

        and: 'all task contain created assignee'
        assert res.content.every { t -> t.assignee.id == assignee.id }
    }

    def 'create task'() {
        given: 'project'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'create task request'
        def request = getCreateTaskRequest().tap { projectId = project.id }

        when: 'create task'
        def task = tasksService.create(request)

        then: 'task is correct'
        with(task) {
            assert id
            title == request.title
            description == request.description
            type == request.type
            priority == request.priority
            status == Status.NEW
            createdAt.isBefore(Instant.now())
            updatedAt.isBefore(Instant.now())
            createdBy.id == user.id
            updatedBy.id == user.id
        }
    }

    def 'update task'() {
        given: 'project'
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
        tasksService.update(task.id, request)

        then: 'task updated'
        def res = tasksService.findById(task.id)
        with(res) {
            title == request.title
            description == request.description
            type == request.type
            priority == request.priority
            status == request.status
            updatedBy.id == user.id
        }
    }

    def 'patch task'() {
        // TODO: Implement!
    }

    def 'delete task by id'() {
        given: 'project'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'task exists'
        def task = tasksService.create(getCreateTaskRequest().tap {
            projectId = project.id
        })

        when: 'delete task by id'
        tasksService.deleteById(task.id)

        then: 'try to find task'
        def res = tasksRepository.findById(task.id)

        and: 'task deleted'
        res.isEmpty()
    }

    def 'assign task to user'() {
        given: 'project'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'task exists'
        def task = tasksService.create(getCreateTaskRequest().tap {
            projectId = project.id
        })

        and: 'user exists'
        def user = usersService.create(getCreateUserRequest())

        when: 'assign task to user'
        def res = tasksService.assignTaskToUser(task.id, UserTaskRequestDTO.builder()
                .userId(user.id)
                .build())

        then: 'task should be assigned to user'
        res.assignee.id.equals(user.id)
    }

    def 'change task assignee'() {
        given: 'project'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'task exists'
        def task = tasksService.create(getCreateTaskRequest().tap {
            projectId = project.id
        })

        and: 'current task assignee'
        def currentAssignee = usersService.create(getCreateUserRequest())

        and: 'new task assignee'
        def assignee = usersService.create(getCreateUserRequest())

        and: 'task has assignee'
        tasksService.assignTaskToUser(task.id, UserTaskRequestDTO.builder()
                .userId(currentAssignee.id)
                .build())

        when: 'update task assignee'
        def res = tasksService.changeTaskAssignee(task.id, currentAssignee.id, UserTaskRequestDTO.builder()
                .userId(assignee.id)
                .build())

        then: 'task should be assigned to other user'
        res.assignee.id.equals(assignee.id)
    }

    def 'delete task assignee'() {
        given: 'project'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'task exists'
        def task = tasksService.create(getCreateTaskRequest().tap {
            projectId = project.id
        })

        and: 'user exists'
        def user = usersService.create(getCreateUserRequest())

        and: 'task has assignee'
        tasksService.assignTaskToUser(task.id, UserTaskRequestDTO.builder()
                .userId(user.id)
                .build())

        when: 'assign task to user'
        def res = tasksService.deleteTaskAssignee(task.id, user.id)

        then: 'task should has no assignee'
        res.assignee.equals(null)

    }
}
