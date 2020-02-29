package com.app.bugtracker.projects.dto;

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
 * Contains project data.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDTO {

    /**
     * Project id.
     */
    private UUID id;

    /**
     * Project title.
     */
    private String title;

    /**
     * Project description.
     */
    private String description;

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
     * User who created the project.
     */
    private UserDTO createdBy;

    /**
     * User who updated the project.
     */
    private UserDTO updatedBy;
}
