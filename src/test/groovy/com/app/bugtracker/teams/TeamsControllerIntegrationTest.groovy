package com.app.bugtracker.teams

import com.app.bugtracker.BaseControllerIntegrationTest
import com.app.bugtracker.auth.services.ITokensService
import com.app.bugtracker.projects.dto.ProjectDTO
import com.app.bugtracker.projects.dto.TeamProjectRequest
import com.app.bugtracker.projects.models.Project
import com.app.bugtracker.projects.services.IProjectsService
import com.app.bugtracker.projects.services.ITeamsService
import com.app.bugtracker.users.models.User
import com.app.bugtracker.users.services.IUsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.web.reactive.function.BodyInserters

import static com.app.bugtracker.Urls.PROJECT_TEAM
import static com.app.bugtracker.Utils.*
import static org.springframework.http.HttpHeaders.AUTHORIZATION
import static org.springframework.http.MediaType.APPLICATION_JSON

/**
 * Integration tests for Teams controller.
 */
class TeamsControllerIntegrationTest extends BaseControllerIntegrationTest {

    @Autowired
    IUsersService usersService

    @Autowired
    ITokensService tokensService

    @Autowired
    IProjectsService projectsService

    @Autowired
    ITeamsService teamsService

    private User user = null

    private Project project = null

    def setup() {
        user = usersService.create(getCreateUserRequest())
        authenticate(user)

        project = projectsService.create(getCreateProjectRequest())
    }

    def 'add users to project'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'users are created'
        def users = (1..5).collect {
            usersService.create(getCreateUserRequest())
        }

        and: 'users ids'
        Set<UUID> userIds = users.collect { it.id }

        and: 'request'
        def request = TeamProjectRequest.builder()
                .userIds(userIds)
                .build()

        expect: 'add users to project'
        webTestClient.post()
                .uri(PROJECT_TEAM, project.id)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ProjectDTO)
                .consumeWith({ p ->
                    assert p.responseBody.id == project.id
                    assert p.responseBody.users.collect { it.id }.containsAll(userIds)
                })
    }

    def 'remove users from project'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'users are created'
        def users = (1..5).collect {
            usersService.create(getCreateUserRequest())
        }

        and: 'users that will be removed are created'
        def usersToRemove = (1..5).collect {
            usersService.create(getCreateUserRequest())
        }

        and: 'users ids to remove'
        Set<UUID> userIds = usersToRemove.collect { it.id }

        and: 'assign users to project'
        teamsService.addUsersToProject(project.id, TeamProjectRequest.builder()
                .userIds(userIds + users.collect { it.id })
                .build())

        and: 'request'
        def request = TeamProjectRequest.builder()
                .userIds(userIds)
                .build()

        expect: 'remove users from project'
        webTestClient.method(HttpMethod.DELETE)
                .uri(PROJECT_TEAM, project.id)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ProjectDTO)
                .consumeWith({ p ->
                    assert p.responseBody.id == project.id
                    assert p.responseBody.users.size() == users.size()
                    assert !p.responseBody.users.collect { it.id }.containsAll(userIds)
                })
    }
}
