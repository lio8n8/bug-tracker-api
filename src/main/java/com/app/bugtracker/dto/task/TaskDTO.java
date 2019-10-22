package com.app.bugtracker.dto.task;

import java.util.UUID;

import com.app.bugtracker.models.task.TaskPriority;
import com.app.bugtracker.models.task.TaskStatus;
import com.app.bugtracker.models.task.TaskType;

import io.swagger.annotations.ApiModel;
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
@ApiModel(description = "Task dto")
public class TaskDTO {
    private UUID id;
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskType type;
    private TaskStatus status;
}
