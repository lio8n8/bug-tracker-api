package com.app.bugtracker.tasks.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Assign/remove task to/from user request.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTaskRequestDTO {

    /**
     * User id.
     */
    @ApiModelProperty("User id.")
    @NotNull(message = "User id required.")
    private UUID userId;
}
