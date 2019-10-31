package com.app.bugtracker.integration

import com.app.bugtracker.models.task.Task
import com.app.bugtracker.models.task.TaskPriority
import com.app.bugtracker.models.task.TaskStatus
import com.app.bugtracker.models.task.TaskType
import com.app.bugtracker.models.user.User
import com.github.javafaker.Faker

class TestUtils {
    public static Faker faker = new Faker()

    public static User getUser() {

        return User.builder()
                .id(UUID.randomUUID())
                .email(faker.internet().emailAddress())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build()
    }

    public static Task getTask() {

        return Task.builder()
                .title(faker.lorem.sentence())
                .description(faker.lorem.paragraph())
                .type(TaskType.TASK)
                .priority(TaskPriority.NORMAL)
                .status(TaskStatus.OPEN)
                .build()
    }

    public static Task getTask(User user) {

        return Task.builder()
                .title(faker.lorem.sentence())
                .description(faker.lorem.paragraph())
                .type(TaskType.TASK)
                .priority(TaskPriority.NORMAL)
                .status(TaskStatus.OPEN)
                .createdBy(user)
                .build()
    }
}
