package com.app.bugtracker.projects.services;

import com.app.bugtracker.projects.dto.TeamProjectRequest;
import com.app.bugtracker.projects.models.Project;

import java.util.UUID;

/**
 * Teams service interface.
 */
public interface ITeamsService {

    /**
     * Adds new users to project.
     *
     * @param id project id
     * @param request to add users in project
     * @return {@link Project}
     */
    Project addUsersToProject(UUID id, TeamProjectRequest request);

    /**
     * Removes users from project.
     *
     * @param id project id
     * @param request to remove users from project
     * @return {@link Project}
     */
    Project removeUsersFromProject(UUID id, TeamProjectRequest request);
}
