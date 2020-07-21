package com.app.bugtracker.projects.services;

import com.app.bugtracker.projects.dto.TeamProjectRequest;
import com.app.bugtracker.projects.models.Project;

/**
 * Teams service interface.
 */
public interface ITeamsService {

    /**
     * Adds new users to project.
     *
     * @param request to add users in project
     * @return {@link Project}
     */
    Project addUsersToProject(TeamProjectRequest request);

    /**
     * Removes users from project.
     *
     * @param request to remove users from project
     * @return {@link Project}
     */
    Project removeUsersFromProject(TeamProjectRequest request);
}
