package com.app.bugtracker.integration

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
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.hateoas.PagedResources
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Shared

import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpMethod.PUT

import javax.persistence.OneToOne

import static org.springframework.http.HttpMethod.DELETE

class TasksControllerIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder

    @Autowired
    private IUsersRepository usersRepository

    @Autowired
    private ITasksRepository tasksRepository

    @Shared
    private Faker faker = new Faker()

    final private TASKS_URL = BASE_URL + Urls.TASKS

    def 'Create task' () {
        given: 'A user'
        def user = createUser()
        and: 'task DTO'
        def createTaskDTO = CreateTaskDTO.builder()
                .title(faker.lorem.sentence())
                .description(faker.lorem.paragraph())
                .type(TaskType.TASK)
                .priority(TaskPriority.NORMAL)
                .status(TaskStatus.IN_PROGRESS)
                .createdBy(user.id)
                .build()

        when: 'Create task'
        HttpEntity<Task> request = new HttpEntity<>(createTaskDTO, TestUtils.getAuthHttpHeaders(user.email))
        def result = restTemplate.exchange(TASKS_URL, POST, request, TaskDTO.class)

        then: 'Response status is CREATED'
        result.statusCode == HttpStatus.CREATED
        and: 'Task title is correct'
        result.body.title == createTaskDTO.title
        and: 'Task description is correct'
        result.body.description == createTaskDTO.description
        and: 'Task has correct status'
        result.body.status == createTaskDTO.status
        and: 'Task has correct type'
        result.body.type == createTaskDTO.type
        and: 'Task has correct priority'
        result.body.priority == createTaskDTO.priority
    }

    def 'Get task by id' () {
        given: 'A user'
        def user = createUser()
        and: 'A task'
        def task = createTask(user)

        when: 'Get task'
        def result = restTemplate.exchange(TASKS_URL + '/' + task.id, GET,
                new HttpEntity<>(TestUtils.getAuthHttpHeaders(user.email)), Task.class)

        then: 'Response tatus is OK'
        result.statusCode == HttpStatus.OK
        and: 'Task has not null id'
        result.body.id
        and: 'Task title is correct'
        result.body.title == task.title
        and: 'Task description is correct'
        result.body.description == task.description
    }

    def 'Get tasks in project' () {
    }

    def 'Edit task' () {
        given: 'A user'
        def user = createUser()
        and: 'A task'
        def task = createTask()
        and: 'An update task DTO'
        def updateTaskDTO = CreateTaskDTO.builder()
                .title(faker.lorem.sentence())
                .description(faker.lorem.paragraph())
                .type(TaskType.TASK)
                .priority(TaskPriority.NORMAL)
                .status(TaskStatus.IN_PROGRESS)
                .createdBy(user.id)
                .build()

        when: 'Update task'
        HttpEntity<Task> request = new HttpEntity<>(updateTaskDTO,
                TestUtils.getAuthHttpHeaders(user.email))
        def result = restTemplate.exchange(TASKS_URL + '/' + task.id, PUT, request, Task.class)

        then: 'Response is OK'
        result.statusCode == HttpStatus.OK
    }

    def 'Delete task by id' () {
        given: 'A user'
        def user = createUser()
        and: 'A task'
        def task = createTask()

        when: 'Deleting task'
        def result = restTemplate.exchange(TASKS_URL + '/' + task.id, DELETE,
                new HttpEntity<>(TestUtils.getAuthHttpHeaders(user.email)), Task.class)

        then: 'Response should be empty'
        result.statusCode == HttpStatus.NO_CONTENT
    }

    def private createTask(User createdBy) {
        return tasksRepository.save(Task.builder()
                .title(faker.lorem.sentence())
                .description(faker.lorem.paragraph())
                .type(TaskType.TASK)
                .priority(TaskPriority.NORMAL)
                .status(TaskStatus.OPEN)
                .createdBy(createdBy)
                .assignedTo(null)
                .build());
    }

    def private createUser() {
        return usersRepository.save(User.builder()
                .email(faker.internet.emailAddress())
                .psw(bCryptPasswordEncoder.encode(faker.internet.password()))
                .build())
    }
}
