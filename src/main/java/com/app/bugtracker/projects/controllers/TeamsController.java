package com.app.bugtracker.projects.controllers;

import com.app.bugtracker.projects.dto.ProjectDTO;
import com.app.bugtracker.projects.dto.TeamProjectRequest;
import com.app.bugtracker.projects.services.ITeamsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.UUID;

import static com.app.bugtracker.Urls.PROJECT_TEAM;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Implementations of {@link ITeamsController}.
 */
@RestController
@Api(tags = "teams-controller")
@Slf4j
public class TeamsController implements ITeamsController {

    /**
     * Teams service.
     */
    private final ITeamsService teamsService;

    /**
     * Conversion service.
     */
    private final ConversionService conversionService;

    public TeamsController(final ITeamsService teamsService,
                           final ConversionService conversionService) {
        this.teamsService = teamsService;
        this.conversionService = conversionService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping(path = PROJECT_TEAM,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("Add users to project.")
    public ResponseEntity<ProjectDTO> addUsersToProject(
            @PathVariable("id") final UUID id,
            @Valid @RequestBody final TeamProjectRequest request) {

        return new ResponseEntity<>(conversionService.convert(
            teamsService.addUsersToProject(id, request), ProjectDTO.class
        ), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DeleteMapping(path = PROJECT_TEAM,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("Remove users from project.")
    public ResponseEntity<ProjectDTO> removeUsersFromProject(
            @PathVariable("id") final UUID id,
            @Valid @RequestBody final TeamProjectRequest request) {

        return new ResponseEntity<>(conversionService.convert(
                teamsService.removeUsersFromProject(id, request), ProjectDTO.class
        ), OK);
    }
}
