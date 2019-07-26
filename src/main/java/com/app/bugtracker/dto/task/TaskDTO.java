package com.app.bugtracker.dto.task;

import java.util.UUID;

import com.app.bugtracker.constants.Priority;
import com.app.bugtracker.constants.TaskStatus;
import com.app.bugtracker.constants.TaskType;

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
    private Priority priority;
    private TaskType type;
    private TaskStatus status;
}
