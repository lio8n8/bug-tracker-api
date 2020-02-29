package com.app.bugtracker

import com.app.bugtracker.configs.ApplicationConfigs
import com.app.bugtracker.projects.dto.ProjectRequest
import com.app.bugtracker.tasks.dto.TaskRequest
import com.app.bugtracker.tasks.models.Priority
import com.app.bugtracker.tasks.models.Status
import com.app.bugtracker.tasks.models.Task
import com.app.bugtracker.tasks.models.Type
import com.app.bugtracker.users.dto.UserCreateRequest
import com.app.bugtracker.users.models.User
import com.app.bugtracker.projects.models.Project
import com.github.javafaker.Faker
import org.springframework.security.authentication.TestingAuthenticationToken

import java.time.LocalDateTime
import static org.springframework.security.core.context.SecurityContextHolder.getContext

// TODO: Refactor.
class Utils {
    public static Faker faker = new Faker()
    public static Random random = new Random()

    public static User getUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .psw(faker.internet().password())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .locked(false)
                .build()
    }

    public static getTask() {
        User user = getUser()

        return Task.builder()
                .id(UUID.randomUUID())
                .title(faker.lorem().sentence())
                .description(faker.lorem().sentence())
                .type(Type.TASK)
                .priority(Priority.TRIVIAL)
                .status(Status.NEW)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(user)
                .updatedBy(user)
                .project(getProject())
                .build()
    }

    public static getProject() {
        User user = getUser()

        return Project.builder()
                .id(UUID.randomUUID())
                .title(faker.lorem().sentence())
                .description(faker.lorem().sentence())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(user)
                .updatedBy(user)
                .build()
    }

    public static UserCreateRequest getCreateUserRequest() {
        String psw = faker.internet().password()

        return UserCreateRequest.builder()
                .email(faker.internet().emailAddress())
                .username(faker.name().username())
                .password(psw)
                .confirmPassword(psw)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build()
    }

    public static TaskRequest getCreateTaskRequest() {
        return TaskRequest.builder()
                .title(faker.lorem().sentence())
                .description(faker.lorem().sentence())
                .type(Type.TASK)
                .priority(Priority.TRIVIAL)
                .status(Status.NEW)
                .build()
    }

    public static ProjectRequest getCreateProjectRequest() {
        return ProjectRequest.builder()
                .title(faker.lorem().sentence())
                .description(faker.lorem().sentence())
                .build()
    }

    public static List<User> getUsers() {
        int x = getRandomInteger(42)
        List<User> users = new ArrayList<>(x)

        for (int i = 0; i < x; i++) {
            users.add(getUser())
        }

        return users
    }

    public static List<Task> getTasks() {
        int x = getRandomInteger(42)
        List<Task> tasks = new ArrayList<>(x)

        for (int i = 0; i < x; i++) {
            tasks.add(getTask())
        }

        return tasks
    }

    public static List<Project> getProjects() {
        int x = getRandomInteger(42)
        List<Project> projects = new ArrayList<>(x)

        for (int i = 0; i < x; i++) {
            projects.add(getProject())
        }

        return projects
    }

    public static int getRandomInteger(Integer x) {
        return random.nextInt(x)
    }

    public static String getRandomWord() {
        return faker.lorem().word()
    }

    public static ApplicationConfigs getApplicationConfigs() {
        return new ApplicationConfigs().with {
            tokenConfigs = new ApplicationConfigs.TokenConfigs().builder()
                    .secretKey('secretKey')
                    .expiredIn(3600000)
                    .build()

            it
        }
    }

    public static void authenticate(User user) {
        getContext().setAuthentication(
                new TestingAuthenticationToken(user.username, null))
    }
}
