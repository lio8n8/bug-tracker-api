package com.app.bugtracker.dto.task;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Assign task to user dto.")
public class AssignTaskRequest {

    @NotNull(message = "Task id required.")
    private UUID taskId;

    @NotNull(message = "User id required.")
    private UUID userId;
}
