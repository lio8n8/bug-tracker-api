package com.app.bugtracker.tasks.dto;

import com.app.bugtracker.tasks.models.Priority;
import com.app.bugtracker.tasks.models.Status;
import com.app.bugtracker.tasks.models.Type;
import com.app.bugtracker.users.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * Time of updating.
     */
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime updatedAt;

    /**
     * User who created the task.
     */
    private UserDTO createdBy;

    /**
     * User who updated the task.
     */
    private UserDTO updatedBy;
}
