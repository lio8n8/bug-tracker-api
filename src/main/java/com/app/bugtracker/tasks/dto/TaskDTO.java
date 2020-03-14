package com.app.bugtracker.tasks.dto;

import com.app.bugtracker.serializers.InstantDeserializer;
import com.app.bugtracker.serializers.InstantSerializer;
import com.app.bugtracker.tasks.models.Priority;
import com.app.bugtracker.tasks.models.Status;
import com.app.bugtracker.tasks.models.Type;
import com.app.bugtracker.users.dto.UserDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * Contains task data.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {

    /**
     * Task id.
     */
    private UUID id;

    /**
     * Task title.
     */
    private String title;

    /**
     * Task description.
     */
    private String description;

    /**
     * Task type.
     */
    private Type type;

    /**
     * Task priority.
     */
    private Priority priority;

    /**
     * Task status.
     */
    private Status status;

    /**
     * Time of creation.
     */
    @JsonDeserialize(using = InstantDeserializer.class)
    @JsonSerialize(using = InstantSerializer.class)
    private Instant createdAt;

    /**
     * Time of updating.
     */
    @JsonDeserialize(using = InstantDeserializer.class)
    @JsonSerialize(using = InstantSerializer.class)
    private Instant updatedAt;

    /**
     * User who created the task.
     */
    private UserDTO createdBy;

    /**
     * User who updated the task.
     */
    private UserDTO updatedBy;

    /**
     * Task assignee.
     */
    private UserDTO assignee;
}
