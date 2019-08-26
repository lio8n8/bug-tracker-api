package com.app.bugtracker.integration

import com.github.javafaker.Faker
import com.app.bugtracker.dto.task.CreateTaskDTO
import com.app.bugtracker.dto.task.TaskDTO
import com.app.bugtracker.repositories.ITasksRepository
import com.app.bugtracker.repositories.IUsersRepository
import com.app.bugtracker.models.User
import com.app.bugtracker.models.Task
import com.app.bugtracker.constants.UserRoles
import com.app.bugtracker.constants.TaskPriority
import com.app.bugtracker.constants.TaskStatus
import com.app.bugtracker.constants.TaskType
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

    final private USERS_URL = BASE_URL + Urls.TASKS

    def 'Create task' () {
    }

    def 'Get task by id' () {
    }

    def 'Get tasks in project' () {
    }

    def 'Edit task' () {
    }

    def 'Delete task by id' () {
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
