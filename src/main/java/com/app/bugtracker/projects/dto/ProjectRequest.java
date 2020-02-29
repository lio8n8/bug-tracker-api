package com.app.bugtracker.projects.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * Project create/update request.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRequest {

    @ApiModelProperty("Project title")
    @NotEmpty(message = "Project title required.")
    private String title;

    @ApiModelProperty("Project description")
    @NotEmpty(message = "Project description required.")
    private String description;
}
