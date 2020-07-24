package com.app.bugtracker.projects.dto;

import com.app.bugtracker.serializers.InstantDeserializer;
import com.app.bugtracker.serializers.InstantSerializer;
import com.app.bugtracker.users.dto.UserDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
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
    @JsonDeserialize(using = InstantDeserializer.class)
    @JsonSerialize(using = InstantSerializer.class)
    private Instant createdAt;

    /**
     * Time of updating.
     */
    @JsonDeserialize(using = InstantDeserializer.class)
    @JsonSerialize(using = InstantSerializer.class)
    private Instant updatedAt;

    /**
     * User who created the project.
     */
    private UserDTO createdBy;

    /**
     * User who updated the project.
     */
    private UserDTO updatedBy;

    /**
     * Users assigned to project.
     */
    private List<UserDTO> users;
}
