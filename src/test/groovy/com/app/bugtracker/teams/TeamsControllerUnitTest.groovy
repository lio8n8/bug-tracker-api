package com.app.bugtracker.teams

import com.app.bugtracker.projects.controllers.TeamsController
import com.app.bugtracker.projects.dto.ProjectDTO
import com.app.bugtracker.projects.dto.TeamProjectRequest
import com.app.bugtracker.projects.services.ITeamsService
import org.springframework.core.convert.ConversionService
import spock.lang.Specification

import static com.app.bugtracker.Utils.*
import static org.springframework.http.HttpStatus.*

/**
 * Unit tests for Teams controller.
 */
class TeamsControllerUnitTest extends Specification {
    def 'add users to project'() {
        given: 'teams service mock'
        def teamsServiceMock = Mock(ITeamsService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'teams controller'
        def teamsController = new TeamsController(
                teamsServiceMock,
                conversionServiceMock
        )

        and: 'project'
        def project = getProject()

        and: 'users'
        def users = (1..5).collect { getUser() }

        and: 'request'
        def request = TeamProjectRequest.builder()
                .userIds(users.collect { it.id } as Set)
                .build()

        when: 'add users to project'
        def res = teamsController.addUsersToProject(project.id, request)

        then: 'project updated'
        1 * teamsServiceMock.addUsersToProject(project.id, request) >> project.tap {
            team = users
        }

        and: 'conversion service called'
        1 * conversionServiceMock.convert(project, ProjectDTO) >> ProjectDTO.builder().build()

        and: 'response status is OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'remove users from project'() {
        given: 'teams service mock'
        def teamsServiceMock = Mock(ITeamsService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'teams controller'
        def teamsController = new TeamsController(
                teamsServiceMock,
                conversionServiceMock
        )

        and: 'users'
        def users = (1..5).collect { getUser() }

        and: 'project'
        def project = getProject().tap { team = users}

        and: 'request'
        def request = TeamProjectRequest.builder()
                .userIds(users.collect { it.id } as Set)
                .build()

        when: 'remove users from project'
        def res = teamsController.addUsersToProject(project.id, request)

        then: 'project updated'
        1 * teamsServiceMock.addUsersToProject(project.id, request) >> project.tap {
            team = users
        }

        and: 'conversion service called'
        1 * conversionServiceMock.convert(project, ProjectDTO) >> ProjectDTO.builder().build()

        and: 'response status is OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }
}
