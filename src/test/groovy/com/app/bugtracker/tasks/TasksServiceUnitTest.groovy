package com.app.bugtracker.tasks

import com.app.bugtracker.auth.services.IAuthContext
import com.app.bugtracker.projects.services.IProjectsService
import com.app.bugtracker.tasks.dto.TaskRequest
import com.app.bugtracker.tasks.dto.UserTaskRequestDTO
import com.app.bugtracker.tasks.models.Priority
import com.app.bugtracker.tasks.models.Task
import com.app.bugtracker.tasks.models.Type
import com.app.bugtracker.tasks.repositories.ITasksRepository
import com.app.bugtracker.tasks.services.TasksService
import com.app.bugtracker.users.services.IUsersService
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

        and: 'projects service mock'
        def projectServiceMock = Mock(IProjectsService)

        and: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'tasks service'
        def taskService = new TasksService(
                tasksRepositoryMock,
                projectServiceMock,
                usersServiceMock,
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

        and: 'projects service mock'
        def projectServiceMock = Mock(IProjectsService)

        and: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'tasks service'
        def taskService = new TasksService(
                tasksRepositoryMock,
                projectServiceMock,
                usersServiceMock,
                authContextMock
        )

        and: 'task'
        def task = getTask()

        when: 'find task by id'
        taskService.findById(task.id)

        then: 'task returned'
        1 * tasksRepositoryMock.findById(task.id) >> Optional.of(task)
    }

    def 'find task by assignee id'() {
        given: 'tasks repository mock'
        def tasksRepositoryMock = Mock(ITasksRepository)

        and: 'projects service mock'
        def projectServiceMock = Mock(IProjectsService)

        and: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'tasks service'
        def taskService = new TasksService(
                tasksRepositoryMock,
                projectServiceMock,
                usersServiceMock,
                authContextMock
        )

        and: 'page request'
        def request = PageRequest.of(0, 42)

        and: 'user'
        def user = getUser()

        when: 'find tasks by assignee id'
        taskService.findByAssigneeId(user.id, request)

        then: 'findByAssignee method called'
        1 * tasksRepositoryMock.findByAssigneeId(user.id, request)
    }

    def 'find task assigned for current user'() {
        given: 'tasks repository mock'
        def tasksRepositoryMock = Mock(ITasksRepository)

        and: 'projects service mock'
        def projectServiceMock = Mock(IProjectsService)

        and: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'tasks service'
        def taskService = new TasksService(
                tasksRepositoryMock,
                projectServiceMock,
                usersServiceMock,
                authContextMock
        )

        and: 'page request'
        def request = PageRequest.of(0, 42)

        and: 'user'
        def user = getUser()

        when: 'find tasks assigned for current user'
        taskService.findByCurrentUser(request)

        then: 'user exists and authenticated'
        1 * authContextMock.getUser() >> user

        and: 'findByAssignee method called'
        1 * tasksRepositoryMock.findByAssigneeId(user.id, request)
    }

    def 'create task' () {
        given: 'tasks repository mock'
        def tasksRepositoryMock = Mock(ITasksRepository)

        and: 'projects service mock'
        def projectServiceMock = Mock(IProjectsService)

        and: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'tasks service'
        def taskService = new TasksService(
                tasksRepositoryMock,
                projectServiceMock,
                usersServiceMock,
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

        and: 'projects service mock'
        def projectServiceMock = Mock(IProjectsService)

        and: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'tasks service'
        def taskService = new TasksService(
                tasksRepositoryMock,
                projectServiceMock,
                usersServiceMock,
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

        and: 'projects service mock'
        def projectServiceMock = Mock(IProjectsService)

        and: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'tasks service'
        def taskService = new TasksService(
                tasksRepositoryMock,
                projectServiceMock,
                usersServiceMock,
                authContextMock
        )

        and: 'task'
        def task = getTask()

        when: 'delete task'
        taskService.deleteById(task.id)

        then: 'task deleted'
        1 * tasksRepositoryMock.deleteById(task.id)
    }

    def 'assign task to user'() {
        given: 'tasks repository mock'
        def tasksRepositoryMock = Mock(ITasksRepository)

        and: 'projects service mock'
        def projectServiceMock = Mock(IProjectsService)

        and: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'tasks service'
        def taskService = new TasksService(
                tasksRepositoryMock,
                projectServiceMock,
                usersServiceMock,
                authContextMock
        )

        and: 'task'
        def task = getTask()

        and: 'assignee'
        def assignee = getUser()

        when: 'assign task to user'
        taskService.assignTaskToUser(task.id, UserTaskRequestDTO.builder()
                .userId(assignee.id)
                .build())

        then: 'task exists'
        1 * tasksRepositoryMock.findById(task.id) >> Optional.of(task)

        and: 'user exists'
        1 * usersServiceMock.findById(assignee.id) >> assignee

        and: 'save task method called'
        1 * tasksRepositoryMock.save(!null as Task)
    }

    def 'change task assignee'() {
        given: 'tasks repository mock'
        def tasksRepositoryMock = Mock(ITasksRepository)

        and: 'projects service mock'
        def projectServiceMock = Mock(IProjectsService)

        and: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'tasks service'
        def taskService = new TasksService(
                tasksRepositoryMock,
                projectServiceMock,
                usersServiceMock,
                authContextMock
        )

        and: 'task'
        def task = getTask()

        and: 'current assignee'
        def currentAssignee = getUser()

        and: 'a new assignee'
        def assignee = getUser()

        when: 'assign change task assignee'
        taskService.changeTaskAssignee(task.id, currentAssignee.id, UserTaskRequestDTO.builder()
                .userId(assignee.id)
                .build())

        then: 'task exists'
        1 * tasksRepositoryMock.findById(task.id) >> Optional.of(task)

        and: 'current assignee exists'
        1 * usersServiceMock.findById(currentAssignee.id) >> currentAssignee

        and: 'a new assignee exists'
        1 * usersServiceMock.findById(assignee.id) >> assignee

        and: 'save task method called'
        1 * tasksRepositoryMock.save(!null as Task)
    }

    def 'delete task assignee'() {
        given: 'tasks repository mock'
        def tasksRepositoryMock = Mock(ITasksRepository)

        and: 'projects service mock'
        def projectServiceMock = Mock(IProjectsService)

        and: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'tasks service'
        def taskService = new TasksService(
                tasksRepositoryMock,
                projectServiceMock,
                usersServiceMock,
                authContextMock
        )

        and: 'task'
        def task = getTask()

        and: 'assignee'
        def assignee = getUser()

        when: 'delete task assignee'
        taskService.deleteTaskAssignee(task.id, assignee.id)

        then: 'task exists'
        1 * tasksRepositoryMock.findById(task.id) >> Optional.of(task)

        and: 'assignee exists'
        1 * usersServiceMock.findById(assignee.id) >> assignee

        and: 'save task method called'
        1 * tasksRepositoryMock.save(!null as Task)
    }
}
