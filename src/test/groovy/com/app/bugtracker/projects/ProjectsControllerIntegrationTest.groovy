package com.app.bugtracker.projects

import com.app.bugtracker.BaseControllerIntegrationTest
import com.app.bugtracker.auth.services.ITokensService
import com.app.bugtracker.projects.dto.ProjectDTO
import com.app.bugtracker.projects.services.IProjectsService
import com.app.bugtracker.tasks.dto.TaskDTO
import com.app.bugtracker.users.services.IUsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.reactive.function.BodyInserters

import java.time.LocalDateTime

import static com.app.bugtracker.Urls.PROJECT
import static com.app.bugtracker.Urls.PROJECTS
import static com.app.bugtracker.Urls.TASK
import static com.app.bugtracker.Urls.TASKS
import static com.app.bugtracker.Utils.authenticate
import static com.app.bugtracker.Utils.getCreateProjectRequest
import static com.app.bugtracker.Utils.getCreateTaskRequest
import static com.app.bugtracker.Utils.getCreateUserRequest
import static org.springframework.http.HttpHeaders.AUTHORIZATION
import static org.springframework.http.MediaType.APPLICATION_JSON

/**
 * Integration tests for Projects controller.
 */
class ProjectsControllerIntegrationTest extends BaseControllerIntegrationTest {

    @Autowired
    IUsersService usersService

    @Autowired
    ITokensService tokensService

    @Autowired
    IProjectsService projectsService

    def 'find project by id'() {
        given: 'create user request'
        def createUserReq = getCreateUserRequest()

        and: 'user created'
        def user = usersService.create(createUserReq)

        and: 'token'
        def token = tokensService.createToken(user.username)

        and: 'user is authenticated'
        authenticate(user)

        and: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        when: 'find project by id'
        webTestClient.get()
                .uri(PROJECT, project.id)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ProjectDTO)
                .consumeWith({ p ->
                    assert p.responseBody.id == project.id
                    assert p.responseBody.title == project.title
                    assert p.responseBody.description == project.description
                    assert p.responseBody.createdBy.id == user.id
                    assert p.responseBody.updatedBy.id == user.id
                    assert p.responseBody.createdAt
                    assert p.responseBody.updatedAt
                })

        then: 'success'
        true
    }

    def 'find all projects'() {
        given: 'create user request'
        def createUserReq = getCreateUserRequest()

        and: 'user created'
        def user = usersService.create(createUserReq)

        and: 'token'
        def token = tokensService.createToken(user.username)

        and: 'user is authenticated'
        authenticate(user)

        and: 'project created'
        def project = projectsService.create(getCreateProjectRequest())


        when: 'find all projects'
        webTestClient.get()
                .uri(PROJECTS)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()

        then: 'success'
        true
    }

    def 'create project'() {
        given: 'create user request'
        def createUserReq = getCreateUserRequest()

        and: 'user created'
        def user = usersService.create(createUserReq)

        and: 'token'
        def token = tokensService.createToken(user.username)

        and: 'user is authenticated'
        authenticate(user)

        and: 'create project request'
        def request = getCreateProjectRequest()

        when: 'create project'
        webTestClient.post()
                .uri(PROJECTS)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(ProjectDTO)
                .consumeWith({ p ->
                    assert p.responseBody.id
                    assert p.responseBody.title == request.title
                    assert p.responseBody.description == request.description
                    assert p.responseBody.createdBy.id == user.id
                    assert p.responseBody.updatedBy.id == user.id
                    assert p.responseBody.createdAt.isBefore(LocalDateTime.now())
                    assert p.responseBody.updatedAt.isBefore(LocalDateTime.now())
                })

        then: 'success'
        true
    }

    def 'update project'() {
        given: 'create user request'
        def createUserReq = getCreateUserRequest()

        and: 'user created'
        def user = usersService.create(createUserReq)

        and: 'token'
        def token = tokensService.createToken(user.username)

        and: 'user is authenticated'
        authenticate(user)

        and: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'update project request'
        def request = getCreateProjectRequest()

        when: 'update project'
        webTestClient.put()
                .uri(PROJECT, project.id)
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
                    assert p.responseBody.title == request.title
                    assert p.responseBody.description == request.description
                    assert p.responseBody.updatedBy.id == user.id
                })

        then: 'success'
        true
    }

    def 'delete project by id'() {
        given: 'create user request'
        def createUserReq = getCreateUserRequest()

        and: 'user created'
        def user = usersService.create(createUserReq)

        and: 'token'
        def token = tokensService.createToken(user.username)

        and: 'user is authenticated'
        authenticate(user)

        and: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        when: 'delete project'
        webTestClient.delete()
                .uri(PROJECT, project.id)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isNoContent()

        then: 'success'
        true
    }
}
