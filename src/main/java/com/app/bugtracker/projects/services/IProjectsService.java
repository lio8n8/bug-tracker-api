package com.app.bugtracker.projects.services;

import com.app.bugtracker.projects.dto.ProjectRequest;
import com.app.bugtracker.projects.models.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Projects service interface.
 */
public interface IProjectsService {

    /**
     * Finds project by id.
     *
     * @param id project id
     * @return {@link Project}
     */
    Project findById(UUID id);

    /**
     * Finds all projects.
     *
     * @param request pageable request
     * @return page with list of {@link Project}
     */
    Page<Project> findAll(Pageable request);

    /**
     * Creates project.
     *
     * @param request create project request
     * @return created {@link Project}
     */
    Project create(ProjectRequest request);

    /**
     * Updates project.
     *
     * @param id project id
     * @param request update project request
     * @return updated {@link Project}
     */
    Project update(UUID id, ProjectRequest request);

    /**
     * Deletes project by id.
     *
     * @param id project id
     */
    void deleteById(UUID id);
}
