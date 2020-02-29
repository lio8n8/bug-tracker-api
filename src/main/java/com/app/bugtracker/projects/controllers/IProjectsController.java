package com.app.bugtracker.projects.controllers;

import com.app.bugtracker.projects.dto.ProjectDTO;
import com.app.bugtracker.projects.dto.ProjectRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * Projects controller interface.
 */
public interface IProjectsController {

    /**
     * Finds project by id.
     *
     * @param id project id
     * @return {@link ProjectDTO}
     */
    ResponseEntity<ProjectDTO> findById(UUID id);

    /**
     * Finds all projects.
     *
     * @param request pageable request
     * @return {@link Page} with list of {@link ProjectDTO}
     */
    ResponseEntity<Page<ProjectDTO>> findAll(Pageable request);

    /**
     * Create project.
     *
     * @param request create project request
     * @return created {@link ProjectDTO}
     */
    ResponseEntity<ProjectDTO> create(ProjectRequest request);

    /**
     * Update project.
     *
     * @param id project id.
     * @param request update project request
     * @return updated {@link ProjectDTO}
     */
    ResponseEntity<ProjectDTO> update(UUID id, ProjectRequest request);

    /**
     * Deletes project by id.
     *
     * @param id project id
     * @return empty response
     */
    ResponseEntity deleteById(UUID id);
}
