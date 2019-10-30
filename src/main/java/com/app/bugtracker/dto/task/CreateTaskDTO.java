package com.app.bugtracker.dto.task;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.app.bugtracker.models.task.TaskPriority;
import com.app.bugtracker.models.task.TaskStatus;
import com.app.bugtracker.models.task.TaskType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Create task dto")
public class CreateTaskDTO {

    @NotEmpty(message = "Title is required.")
    @ApiModelProperty("Title")
    private String title;

    @NotEmpty(message = "Description is required.")
    @ApiModelProperty("Description")
    private String description;

    @NotNull(message = "Task priority is required.")
    @ApiModelProperty("Priority")
    private TaskPriority priority;

    @NotNull(message = "Task type is required.")
    @ApiModelProperty("Task type")
    private TaskType type;

    @NotNull(message = "Task status is required.")
    @ApiModelProperty("Task status")
    private TaskStatus status;

    @ApiModelProperty("Id of user")
    private UUID assignedTo;
}
