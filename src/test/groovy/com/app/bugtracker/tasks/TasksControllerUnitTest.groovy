package com.app.bugtracker.tasks

import com.app.bugtracker.tasks.controllers.TasksController
import com.app.bugtracker.tasks.dto.TaskDTO
import com.app.bugtracker.tasks.dto.TaskRequest
import com.app.bugtracker.tasks.dto.UserTaskRequestDTO
import com.app.bugtracker.tasks.models.Priority
import com.app.bugtracker.tasks.models.Status
import com.app.bugtracker.tasks.models.Task
import com.app.bugtracker.tasks.models.Type
import com.app.bugtracker.tasks.services.ITasksService
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import static com.app.bugtracker.Utils.getTask
import static com.app.bugtracker.Utils.getTasks
import static com.app.bugtracker.Utils.getUser
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

/**
 * Unit tests for Tasks controller.
 */
class TasksControllerUnitTest extends Specification {
    def 'find all tasks'() {
        given: 'tasks service mock'
        def tasksServiceMock = Mock(ITasksService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'tasks controller'
        def tasksController = new TasksController(
                tasksServiceMock,
                conversionServiceMock
        )

        and: 'tasks'
        def tasks = getTasks()

        and: 'page'
        def page = new PageImpl<>(tasks)

        and: 'page request'
        def request = PageRequest.of(0, 25)

        when: 'find all tasks'
        def res = tasksController.findAll(request)

        then: 'page was returned'
        1 * tasksServiceMock.findAll(request) >> page

        and: 'conversion service called'
        tasks.size() * conversionServiceMock.convert(!null as Task, TaskDTO)

        and: 'response OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'find task by id'() {
        given: 'tasks service mock'
        def tasksServiceMock = Mock(ITasksService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'tasks controller'
        def tasksController = new TasksController(
                tasksServiceMock,
                conversionServiceMock
        )

        and: 'task'
        def task = getTask()

        when: 'find task by id'
        def res = tasksController.findById(task.id)

        then: 'task returned'
        1 * tasksServiceMock.findById(task.id) >> task

        and: 'conversion service called'
        1 *  conversionServiceMock.convert(task, TaskDTO) >> TaskDTO.builder()
                .id(task.id)
                .title(task.title)
                .description(task.description)
                .type(task.type)
                .priority(task.priority)
                .status(task.status)
                .build()

        and: 'response OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'find task types'() {
        given: 'tasks service mock'
        def tasksServiceMock = Mock(ITasksService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'tasks controller'
        def tasksController = new TasksController(
                tasksServiceMock,
                conversionServiceMock
        )

        and: 'task types'
        def types = Arrays.asList(Type.values())

        when: 'find task types'
        def res = tasksController.findTaskTypes()

        then: 'response OK'
        res.statusCode == OK

        and: 'response body contains task types'
        assert res.body.containsAll(types)
    }

    def 'find task priorities'() {
        given: 'tasks service mock'
        def tasksServiceMock = Mock(ITasksService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'tasks controller'
        def tasksController = new TasksController(
                tasksServiceMock,
                conversionServiceMock
        )

        and: 'task priorities'
        def priorities = Arrays.asList(Priority.values())

        when: 'find task priorities'
        def res = tasksController.findTaskPriorities()

        then: 'response OK'
        res.statusCode == OK

        and: 'response body contains task priorities'
        assert res.body.containsAll(priorities)
    }

    def 'find task statuses'() {
        given: 'tasks service mock'
        def tasksServiceMock = Mock(ITasksService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'tasks controller'
        def tasksController = new TasksController(
                tasksServiceMock,
                conversionServiceMock
        )

        and: 'task statuses'
        def statuses = Arrays.asList(Status.values())

        when: 'find task statuses'
        def res = tasksController.findTaskStatuses()

        then: 'response OK'
        res.statusCode == OK

        and: 'response body contains task statuses'
        assert res.body.containsAll(statuses)
    }

    def 'find tasks by assignee'() {
        given: 'tasks service mock'
        def tasksServiceMock = Mock(ITasksService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'tasks controller'
        def tasksController = new TasksController(
                tasksServiceMock,
                conversionServiceMock
        )

        and: 'tasks'
        def tasks = getTasks()

        and: 'assignee'
        def assignee = getUser()

        and: 'assign task to user'
        tasks.collect {
            t -> t.assignee = assignee
        }

        and: 'page'
        def page = new PageImpl<>(tasks)

        and: 'page request'
        def request = PageRequest.of(0, 25)

        when: 'find tasks by assignee id'
        def res = tasksController.findByAssigneeId(assignee.id, request)

        then: 'page was returned'
        1 * tasksServiceMock.findByAssigneeId(assignee.id, request) >> page

        and: 'conversion service called'
        tasks.size() * conversionServiceMock.convert(!null as Task, TaskDTO)

        and: 'response OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'find tasks assigned to current user'() {
        given: 'tasks service mock'
        def tasksServiceMock = Mock(ITasksService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'tasks controller'
        def tasksController = new TasksController(
                tasksServiceMock,
                conversionServiceMock
        )

        and: 'tasks'
        def tasks = getTasks()

        and: 'assignee'
        def assignee = getUser()

        and: 'assign task to user'
        tasks.collect {
            t -> t.assignee = assignee
        }

        and: 'page'
        def page = new PageImpl<>(tasks)

        and: 'page request'
        def request = PageRequest.of(0, 25)

        when: 'find tasks assigned to current user'
        def res = tasksController.findByCurrentAssignee(request)

        then: 'page was returned'
        1 * tasksServiceMock.findByCurrentUser(request) >> page

        and: 'conversion service called'
        tasks.size() * conversionServiceMock.convert(!null as Task, TaskDTO)

        and: 'response OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'create task'() {
        given: 'tasks service mock'
        def tasksServiceMock = Mock(ITasksService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'tasks controller'
        def tasksController = new TasksController(
                tasksServiceMock,
                conversionServiceMock
        )

        and: 'task'
        def task = getTask()

        and: 'create task request'
        def request = TaskRequest.builder()
                .title(task.title)
                .description(task.description)
                .type(task.type)
                .priority(task.priority)
                .status(task.status)
                .build()

        when: 'create task'
        def res = tasksController.create(request)

        then: 'task created'
        1 * tasksServiceMock.create(request) >> task

        and: 'conversion service called'
        1 *  conversionServiceMock.convert(task, TaskDTO) >> TaskDTO.builder()
                .id(task.id)
                .title(task.title)
                .description(task.description)
                .type(task.type)
                .priority(task.priority)
                .status(task.status)
                .build()

        and: 'response status is CREATED'
        res.statusCode == CREATED

        and: 'response contains body'
        res.body
    }

    def 'update task'() {
        given: 'tasks service mock'
        def tasksServiceMock = Mock(ITasksService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'tasks controller'
        def tasksController = new TasksController(
                tasksServiceMock,
                conversionServiceMock
        )

        and: 'task'
        def task = getTask()

        and: 'update task request'
        def request = TaskRequest.builder()
                .title(task.title)
                .description(task.description)
                .type(task.type)
                .priority(Priority.MAJOR)
                .status(Status.WONTFIX)
                .build()

        when: 'update task'
        def res = tasksController.update(task.id, request)

        then: 'task updated'
        1 * tasksServiceMock.update(task.id, request) >> task.tap {
            priority = request.priority
            status = request.status
        }

        and: 'conversion service called'
        1 * conversionServiceMock.convert(task, TaskDTO) >> TaskDTO.builder()
                .id(task.id)
                .title(task.title)
                .description(task.description)
                .type(task.type)
                .priority(request.priority)
                .status(request.status)
                .build()

        and: 'response status is OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'patch task'() {
        // TODO: Implement!
    }

    def 'delete task by id'() {
        given: 'tasks service mock'
        def tasksServiceMock = Mock(ITasksService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'tasks controller'
        def tasksController = new TasksController(
                tasksServiceMock,
                conversionServiceMock
        )

        and: 'task'
        def task = getTask()

        when: 'delete task by id'
        def res = tasksController.deleteById(task.id)

        then: 'task is deleted'
        1 * tasksServiceMock.deleteById(task.id)

        and: 'response status is NO_CONTENT'
        res.statusCode == NO_CONTENT
    }

    def 'add assignee to task'() {
        given: 'tasks service mock'
        def tasksServiceMock = Mock(ITasksService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'tasks controller'
        def tasksController = new TasksController(
                tasksServiceMock,
                conversionServiceMock
        )

        and: 'task'
        def task = getTask()

        and: 'user'
        def user = getUser()

        and: 'request'
        def request = UserTaskRequestDTO.builder()
                .userId(user.id)
                .build()

        when: 'assign task to user'
        def res = tasksController.assignTaskToUser(task.id, request)

        then: 'assignee was added'
        1 * tasksServiceMock.assignTaskToUser(task.id, request) >> task.tap { assignee = user }

        and: 'conversion service called'
        1 * conversionServiceMock.convert(task, TaskDTO) >> TaskDTO.builder().build()

        and: 'response status is OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'change task assignee'() {
        given: 'tasks service mock'
        def tasksServiceMock = Mock(ITasksService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'tasks controller'
        def tasksController = new TasksController(
                tasksServiceMock,
                conversionServiceMock
        )

        and: 'user'
        def user = getUser()

        and: 'task with assignee'
        def task = getTask().tap { assignee = user }

        and: 'a new assignee'
        def assignee = getUser()

        and: 'request'
        def request = UserTaskRequestDTO.builder()
                .userId(assignee.id)
                .build()

        when: 'change task assignee'
        def res = tasksController.changeTaskAssignee(task.id, user.id, request)

        then: 'assignee was changed'
        1 * tasksServiceMock.changeTaskAssignee(task.id, user.id, request) >> task.tap { assignee }

        and: 'conversion service called'
        1 * conversionServiceMock.convert(task, TaskDTO) >> TaskDTO.builder().build()

        and: 'response status is OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'delete task assignee'() {
        given: 'tasks service mock'
        def tasksServiceMock = Mock(ITasksService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'tasks controller'
        def tasksController = new TasksController(
                tasksServiceMock,
                conversionServiceMock
        )

        and: 'user'
        def user = getUser()

        and: 'task wit assignee'
        def task = getTask().tap {
            assignee = user
        }

        when: 'delete task assignee'
        def res = tasksController.deleteTaskAssignee(task.id, user.id)

        then: 'assignee was deleted'
        1 * tasksServiceMock.deleteTaskAssignee(task.id, user.id) >> task.tap { assignee = null }

        and: 'conversion service called'
        1 * conversionServiceMock.convert(task, TaskDTO) >> TaskDTO.builder().build()

        and: 'response status is OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }
}
