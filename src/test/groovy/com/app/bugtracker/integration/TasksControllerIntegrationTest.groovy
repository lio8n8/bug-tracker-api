package com.app.bugtracker.integration

import com.app.bugtracker.services.auth.IJwtTokenService
import com.github.javafaker.Faker
import com.app.bugtracker.dto.task.CreateTaskDTO
import com.app.bugtracker.dto.task.TaskDTO
import com.app.bugtracker.repositories.ITasksRepository
import com.app.bugtracker.repositories.IUsersRepository
import com.app.bugtracker.models.user.User
import com.app.bugtracker.models.task.Task
import com.app.bugtracker.models.user.UserRoles
import com.app.bugtracker.models.task.TaskPriority
import com.app.bugtracker.models.task.TaskStatus
import com.app.bugtracker.models.task.TaskType
import com.app.bugtracker.constants.Urls

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.reactive.function.BodyInserters

import static org.springframework.http.HttpHeaders.AUTHORIZATION

class TasksControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder

    @Autowired
    private IUsersRepository usersRepository

    @Autowired
    private ITasksRepository tasksRepository

    @Autowired IJwtTokenService tokenService

    def 'create task' () {
        given: 'a user'
        def user = createUser()

        and: 'task DTO'
        def createTaskDTO = CreateTaskDTO.builder()
                .title(TestUtils.faker.lorem.sentence())
                .description(TestUtils.faker.lorem.paragraph())
                .type(TaskType.TASK)
                .priority(TaskPriority.NORMAL)
                .status(TaskStatus.IN_PROGRESS)
                .assignedTo(user.id)
                .build()

        when: 'create task'
        webTestClient.post()
                .uri(Urls.TASKS)
                .body(BodyInserters.fromObject(createTaskDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${tokenService.createToken(user.email)}")
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(TaskDTO.class)
                .consumeWith({ task ->
                    assert task.responseBody.id
                    assert task.responseBody.title == createTaskDTO.title
                    assert task.responseBody.description == createTaskDTO.description
                    assert task.responseBody.type == createTaskDTO.type
                    assert task.responseBody.priority == createTaskDTO.priority
                    assert task.responseBody.status == createTaskDTO.status
                })

        then: 'success'
        true
    }

    def 'get task by id' () {
        given: 'a user'
        def user = createUser()

        and: 'a task'
        def task = createTask(user)

        when: 'get task by id'
        webTestClient.get()
                .uri("${Urls.TASKS}/${task.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${tokenService.createToken(user.email)}")
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
                })

        then: 'success'
        true
    }

    def 'update task' () {
        given: 'a user'
        def user = createUser()

        and: 'a task'
        def task = createTask()

        and: 'an update task request'
        def updateTaskDTO = CreateTaskDTO.builder()
                .title(TestUtils.faker.lorem.sentence())
                .description(TestUtils.faker.lorem.paragraph())
                .type(TaskType.TASK)
                .priority(TaskPriority.NORMAL)
                .status(TaskStatus.IN_PROGRESS)
                .build()

        when: 'update task'
        webTestClient.put()
                .uri("${Urls.TASKS}/${task.id}")
                .body(BodyInserters.fromObject(updateTaskDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${tokenService.createToken(user.email)}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(TaskDTO.class)
                .consumeWith({ t ->
                    assert t.responseBody.title == updateTaskDTO.title
                    assert t.responseBody.description == updateTaskDTO.description
                    assert t.responseBody.type == updateTaskDTO.type
                    assert t.responseBody.priority == updateTaskDTO.priority
                    assert t.responseBody.status == updateTaskDTO.status
                })

        then: 'success'
        true
    }

    def 'delete task by id'() {

        given: 'a user'
        def user = createUser()

        and: 'a task'
        def task = createTask()

        when: 'delete task'
        webTestClient.delete()
                .uri("${Urls.TASKS}/${task.id}")
                .header(AUTHORIZATION, "Bearer ${tokenService.createToken(user.email)}")
                .exchange()
                .expectStatus()
                .isNoContent()

        then: 'success'
        true
    }

    def private createTask(User user) {
        return tasksRepository.save(Task.builder()
                .title(TestUtils.faker.lorem.sentence())
                .description(TestUtils.faker.lorem.paragraph())
                .type(TaskType.TASK)
                .priority(TaskPriority.NORMAL)
                .status(TaskStatus.OPEN)
                .createdBy(user)
                .assignedTo(user)
                .build())
    }

    def private createUser() {
        return usersRepository.save(User.builder()
                .email(TestUtils.faker.internet.emailAddress())
                .psw(bCryptPasswordEncoder.encode(TestUtils.faker.internet.password()))
                .build())
    }
}
