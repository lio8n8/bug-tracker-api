package com.app.bugtracker.DTO.task;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import com.app.bugtracker.constants.Priority;
import com.app.bugtracker.constants.TaskStatus;
import com.app.bugtracker.constants.TaskType;

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

    @NotEmpty(message = "Task priority is required.")
    @ApiModelProperty("Priority")
    private Priority priority;

    @NotEmpty(message = "Task type is required.")
    @ApiModelProperty("Task type")
    private TaskType type;

    @NotEmpty(message = "Task status is required.")
    @ApiModelProperty("Task status")
    private TaskStatus status;

    @NotEmpty(message = "CreatedBy is required")
    @ApiModelProperty("Id author of task")
    private UUID createdBy;

    @ApiModelProperty("Id of user")
    private UUID assignedTo;
}
