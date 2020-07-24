package com.app.bugtracker.projects.controllers;

import com.app.bugtracker.projects.dto.ProjectDTO;
import com.app.bugtracker.projects.dto.TeamProjectRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * Teams controller interface.
 */
public interface ITeamsController {

    /**
     * Adds users to project.
     *
     * @param id project id
     * @param request
     * @return updated {@link ProjectDTO}
     */
    ResponseEntity<ProjectDTO> addUsersToProject(UUID id, TeamProjectRequest request);

    /**
     * Removes users from project.
     *
     * @param id project id
     * @param request
     * @return updated {@link ProjectDTO}
     */
    ResponseEntity<ProjectDTO> removeUsersFromProject(UUID id, TeamProjectRequest request);
}
