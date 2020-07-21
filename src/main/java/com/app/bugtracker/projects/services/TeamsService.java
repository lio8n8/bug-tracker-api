package com.app.bugtracker.projects.services;

import com.app.bugtracker.auth.services.IAuthContext;
import com.app.bugtracker.projects.dto.TeamProjectRequest;
import com.app.bugtracker.projects.models.Project;
import com.app.bugtracker.users.models.User;
import com.app.bugtracker.users.services.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Teams service implementation.
 */
@Service
public class TeamsService implements ITeamsService {

    /**
     * Projects service.
     */
    private final IProjectsService projectsService;

    /**
     * Users service.
     */
    private final IUsersService usersService;

    /**
     * Authentication context.
     */
    private final IAuthContext authContext;

    @Autowired
    public TeamsService(final IProjectsService projectsService,
                        final IUsersService usersService,
                        final IAuthContext authContext) {
        this.projectsService = projectsService;
        this.usersService = usersService;
        this.authContext = authContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project addUsersToProject(TeamProjectRequest request) {
        Project project = projectsService.findById(request.getProjectId());
        Set<User> team = project.getTeam() != null ? project.getTeam() : new HashSet<>();

        for(UUID id: request.getUserIds()) {
            team.add(usersService.findById(id));
        }

        project.setTeam(team);

        return projectsService.save(project);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project removeUsersFromProject(TeamProjectRequest request) {
        Project project = projectsService.findById(request.getProjectId());
        Set<User> team = project.getTeam();

        for(UUID id: request.getUserIds()) {
            team.remove(usersService.findById(id));
        }

        project.setTeam(team);

        return projectsService.save(project);
    }
}
