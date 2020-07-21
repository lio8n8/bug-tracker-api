package com.app.bugtracker.projects.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

/**
 * Contains data for adding/removing users to/from project.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamProjectRequest {

    @ApiModelProperty("Project id")
    @NotNull(message = "Project id required.")
    private UUID projectId;

    @ApiModelProperty("User ids")
    @NotNull(message = "User ids required.")
    private Set<UUID> userIds;
}
