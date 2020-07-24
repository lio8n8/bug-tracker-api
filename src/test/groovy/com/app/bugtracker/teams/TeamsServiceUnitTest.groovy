package com.app.bugtracker.teams

import com.app.bugtracker.auth.services.IAuthContext
import com.app.bugtracker.projects.dto.TeamProjectRequest
import com.app.bugtracker.projects.models.Project
import com.app.bugtracker.projects.services.IProjectsService
import com.app.bugtracker.projects.services.TeamsService
import com.app.bugtracker.users.services.IUsersService
import spock.lang.Specification

import static com.app.bugtracker.Utils.*

/**
 * Unit tests for Teams service.
 */
class TeamsServiceUnitTest extends Specification {

    def 'assign users to project'() {
        given: 'projects service mock'
        def projectServiceMock = Mock(IProjectsService)

        and: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'teams service'
        def teamsService = new TeamsService(
                projectServiceMock,
                usersServiceMock,
                authContextMock
        )

        and: 'project'
        def project = getProject()

        and: 'users'
        def users = (1..5).collect { getUser() }

        and: 'request'
        def request = TeamProjectRequest.builder()
                .userIds(users.collect { user.id } as Set)
                .build()

        when: 'assign users to project'
        teamsService.addUsersToProject(project.id, request)

        then: 'project exists'
        1 * projectServiceMock.findById(project.id) >> project

        and: 'users exists'
        users.size() * usersServiceMock.findById(!null as UUID) >> {
            def userId = it[0] as UUID
            users.find{ user -> user.id == userId }
        }

        and: 'users added to project'
        1 * projectServiceMock.save(!null as Project)
    }

    def 'remove users from project'() {
        given: 'projects service mock'
        def projectServiceMock = Mock(IProjectsService)

        and: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'teams service'
        def teamsService = new TeamsService(
                projectServiceMock,
                usersServiceMock,
                authContextMock
        )

        and: 'users'
        def users = (1..5).collect { getUser() }

        and: 'users to remove'
        def usersToRemove = (1..5).collect { getUser() }

        and: 'project'
        def project = getProject().tap {
            team = users + usersToRemove
        }

        and: 'request'
        def request = TeamProjectRequest.builder()
                .userIds(usersToRemove.collect { user.id } as Set)
                .build()

        when: 'remove users from project'
        teamsService.removeUsersFromProject(project.id, request)

        then: 'project exists'
        1 * projectServiceMock.findById(project.id) >> project

        and: 'users exist'
        usersToRemove.size() * usersServiceMock.findById(!null as UUID) >> {
            def userId = it[0] as UUID
            usersToRemove.find{ user -> user.id == userId }
        }

        and: 'users removed project'
        1 * projectServiceMock.save(!null as Project)
    }
}
