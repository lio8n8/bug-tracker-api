package com.app.bugtracker.tasks.dto;

import com.app.bugtracker.tasks.models.Priority;
import com.app.bugtracker.tasks.models.Status;
import com.app.bugtracker.tasks.models.Type;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Task create/update request.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRequest {

    /**
     * Task title.
     */
    @ApiModelProperty("Task title")
    @NotEmpty(message = "Title required.")
    private String title;

    /**
     * Task description.
     */
    @ApiModelProperty("Task description")
    @NotEmpty(message = "Description required.")
    private String description;

    /**
     * Task type.
     */
    @ApiModelProperty("Task type")
    @NotNull(message = "Task type required.")
    private Type type;

    /**
     * Task priority.
     */
    @ApiModelProperty("Task priority")
    private Priority priority;

    /**
     * Task status.
     */
    @ApiModelProperty("Task status")
    private Status status;
}
