package com.app.bugtracker.projects.services;

import com.app.bugtracker.auth.services.IAuthContext;
import com.app.bugtracker.exceptions.NotFoundException;
import com.app.bugtracker.projects.dto.ProjectRequest;
import com.app.bugtracker.projects.models.Project;
import com.app.bugtracker.projects.repositories.IProjectsRepository;
import com.app.bugtracker.users.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static com.app.bugtracker.exceptions.Exceptions.PROJECT_NOT_FOUND;

/**
 * Project service implementation.
 */
@Service
public class ProjectsService implements IProjectsService {

    /**
     * Projects repository.
     */
    private final IProjectsRepository projectsRepository;

    /**
     * Authentication context.
     */
    private final IAuthContext authContext;

    public ProjectsService(final IProjectsRepository projectsRepository,
                           final IAuthContext authContext) {
        this.projectsRepository = projectsRepository;
        this.authContext = authContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project findById(final UUID id) {
        return projectsRepository.findById(id).orElseThrow(
                () -> new NotFoundException(PROJECT_NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Project> findAll(final Pageable request) {
        return projectsRepository.findAll(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project create(final ProjectRequest request) {
        User user = authContext.getUser();

        return projectsRepository.save(Project.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .createdBy(user)
                .updatedBy(user)
                .build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project update(final UUID id, final ProjectRequest request) {
        Project project = findById(id);
        User user = authContext.getUser();

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setUpdatedAt(Instant.now());
        project.setUpdatedBy(user);

        return projectsRepository.save(project);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(final UUID id) {
        projectsRepository.deleteById(id);
    }
}
