package com.app.bugtracker.tasks

import com.app.bugtracker.BaseServiceIntegrationTest
import com.app.bugtracker.projects.services.IProjectsService
import com.app.bugtracker.tasks.models.Status
import com.app.bugtracker.tasks.repositories.ITasksRepository
import com.app.bugtracker.tasks.services.ITasksService
import com.app.bugtracker.users.models.User
import com.app.bugtracker.users.services.IUsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest

import java.time.LocalDateTime

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

    def 'find task by id'() {
        given: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'task exists'
        def task = tasksService.create(getCreateTaskRequest().tap {
            projectId = project.id
        })

        when: 'find task by id'
        def res = tasksService.findById(task.id)

        then: 'task is returned'
        res && res.id && res.title == task.title
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
            createdAt.isBefore(LocalDateTime.now())
            updatedAt.isBefore(LocalDateTime.now())
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
}
