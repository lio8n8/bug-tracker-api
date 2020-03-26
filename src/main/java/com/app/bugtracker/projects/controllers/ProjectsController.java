package com.app.bugtracker.projects.controllers;

import com.app.bugtracker.projects.dto.ProjectDTO;
import com.app.bugtracker.projects.dto.ProjectRequest;
import com.app.bugtracker.projects.services.IProjectsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

import static com.app.bugtracker.Urls.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Implements {@link IProjectsController}
 */
@RestController
@Api(tags = "projects-controller")
@Slf4j
public class ProjectsController implements IProjectsController {

    /**
     * Projects service.
     */
    private final IProjectsService projectsService;

    /**
     * Conversion service.
     */
    private final ConversionService conversionService;

    public ProjectsController(final IProjectsService projectsService,
                              final ConversionService conversionService) {
        this.projectsService = projectsService;
        this.conversionService = conversionService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = PROJECT)
    @ApiOperation(value = "Find project by id.", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDTO> findById(@PathVariable("id") final UUID id) {

        return new ResponseEntity<>(conversionService.convert(
                projectsService.findById(id), ProjectDTO.class
        ), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = PROJECTS)
    @ApiOperation(value = "Find all projects.")
    public ResponseEntity<Page<ProjectDTO>> findAll(
            @PageableDefault(page = 0, size = 25)
            final Pageable request) {

        return new ResponseEntity<>(projectsService.findAll(request).map(
                p -> conversionService.convert(p, ProjectDTO.class)
        ), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping(path = PROJECTS,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("Create a new project.")
    public ResponseEntity<ProjectDTO> create(
            @Valid @RequestBody final ProjectRequest request) {

        return new ResponseEntity<>(conversionService.convert(
                projectsService.create(request), ProjectDTO.class
        ), CREATED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PutMapping(path = PROJECT,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("Update project.")
    public ResponseEntity<ProjectDTO> update(
            @PathVariable("id") final UUID id,
            @Valid @RequestBody final ProjectRequest request) {

        return new ResponseEntity<>(conversionService.convert(
                projectsService.update(id, request), ProjectDTO.class
        ), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DeleteMapping(path = PROJECT)
    @ApiOperation("Delete project by id.")
    public ResponseEntity deleteById(@PathVariable("id") final UUID id) {
        projectsService.deleteById(id);

        return new ResponseEntity(NO_CONTENT);
    }
}
