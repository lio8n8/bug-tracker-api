package com.app.bugtracker.tasks

import com.app.bugtracker.auth.services.IAuthContext
import com.app.bugtracker.tasks.dto.TaskRequest
import com.app.bugtracker.tasks.models.Priority
import com.app.bugtracker.tasks.models.Task
import com.app.bugtracker.tasks.models.Type
import com.app.bugtracker.tasks.repositories.ITasksRepository
import com.app.bugtracker.tasks.services.TasksService
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import static com.app.bugtracker.Utils.faker
import static com.app.bugtracker.Utils.getTask
import static com.app.bugtracker.Utils.getUser

/**
 * Unit tests for Tasks service.
 */
class TasksServiceUnitTest extends Specification {

    def 'find all tasks'() {
        given: 'tasks repository mock'
        def tasksRepositoryMock = Mock(ITasksRepository)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'tasks service'
        def taskService = new TasksService(
                tasksRepositoryMock,
                authContextMock
        )

        and: 'page request'
        def request = PageRequest.of(0, 42)

        when: 'find all tasks'
        taskService.findAll(request)

        then: 'findAll method called'
        1 * tasksRepositoryMock.findAll(request)
    }

    def 'find task by id'() {
        given: 'tasks repository mock'
        def tasksRepositoryMock = Mock(ITasksRepository)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'tasks service'
        def taskService = new TasksService(
                tasksRepositoryMock,
                authContextMock
        )

        and: 'task'
        def task = getTask()

        when: 'find task by id'
        taskService.findById(task.id)

        then: 'task returned'
        1 * tasksRepositoryMock.findById(task.id) >> Optional.of(task)
    }

    def 'create task' () {
        given: 'tasks repository mock'
        def tasksRepositoryMock = Mock(ITasksRepository)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'tasks service'
        def taskService = new TasksService(
                tasksRepositoryMock,
                authContextMock
        )

        and: 'authenticated user'
        def user = getUser()

        and: 'task'
        def task = getTask()

        and: 'create task request'
        def request = TaskRequest.builder()
                .title(task.title)
                .description(task.description)
                .type(Type.TASK)
                .priority(Priority.TRIVIAL)
                .build()

        when: 'create task'
        taskService.create(request)

        then: 'user authenticated'
        1 * authContextMock.getUser() >> user

        and: 'task created'
        1 * tasksRepositoryMock.save(!null as Task) >> task
    }

    def 'update task'() {
        given: 'tasks repository mock'
        def tasksRepositoryMock = Mock(ITasksRepository)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'tasks service'
        def taskService = new TasksService(
                tasksRepositoryMock,
                authContextMock
        )

        and: 'authenticated user'
        def user = getUser()

        and: 'task'
        def task = getTask()

        and: 'update task request'
        def request = TaskRequest.builder()
                .title(faker.lorem().sentence())
                .description(faker.lorem().sentence())
                .type(Type.TASK)
                .priority(Priority.MAJOR)
                .build()

        when: 'update task'
        taskService.update(task.id, request)

        then: 'task exists'
        1 * tasksRepositoryMock.findById(task.id) >> Optional.of(task)

        and: 'user authenticated'
        1 * authContextMock.getUser() >> user

        and: 'task updated'
        1 * tasksRepositoryMock.save(!null as Task)
    }

    def 'patch task'() {

    }

    def 'delete task by id'() {
        given: 'tasks repository mock'
        def tasksRepositoryMock = Mock(ITasksRepository)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'tasks service'
        def taskService = new TasksService(
                tasksRepositoryMock,
                authContextMock
        )

        and: 'task'
        def task = getTask()

        when: 'delete task'
        taskService.deleteById(task.id)

        then: 'task deleted'
        1 * tasksRepositoryMock.deleteById(task.id)
    }
}
