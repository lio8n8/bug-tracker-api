package com.app.bugtracker.teams

import com.app.bugtracker.BaseServiceIntegrationTest
import com.app.bugtracker.projects.dto.TeamProjectRequest
import com.app.bugtracker.projects.models.Project
import com.app.bugtracker.projects.services.IProjectsService
import com.app.bugtracker.projects.services.ITeamsService
import com.app.bugtracker.users.models.User
import com.app.bugtracker.users.services.IUsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

import static com.app.bugtracker.Utils.*

/**
 * Integrations tests for Teams service.
 */
class TeamsServiceIntegrationTest extends BaseServiceIntegrationTest {

    @Autowired
    IProjectsService projectsService

    @Autowired
    IUsersService usersService

    @Autowired
    ITeamsService teamsService

    private Project project = null
    private User admin = null

    def setup() {
        admin = usersService.create(getCreateUserRequest())
        authenticate(admin)

        project = projectsService.create(getCreateProjectRequest())
    }

    @Transactional
    def 'assign users to project'() {
        given: 'users'
        def users = (1..5).collect {
            usersService.create(getCreateUserRequest())
        }

        and: 'users ids'
        Set<UUID> usersIds = users.collect { it.id }

        when: 'assign users to project'
        teamsService.addUsersToProject(TeamProjectRequest.builder()
                .projectId(project.id)
                .userIds(usersIds)
                .build())

        then: 'users were added to project'
        assert projectsService.findById(project.id).team.collect { it.id }.containsAll(usersIds)
    }

    @Transactional
    def 'remove users from project'() {
        given: 'users'
        def users = (1..10).collect {
            usersService.create(getCreateUserRequest())
        }

        and: 'assign users to project'
        teamsService.addUsersToProject(TeamProjectRequest.builder()
                .projectId(project.id)
                .userIds(users.collect { it.id } as Set<UUID>)
                .build())

        and: 'users ids'
        Set<UUID> usersIds = new HashSet<>()
        for (int i = 0; i < users.size(); i++) {
            if (i % 2) {
                usersIds.add(users[i].id)
            }
        }

        when: 'remove users from project'
        teamsService.removeUsersFromProject(TeamProjectRequest.builder()
                .projectId(project.id)
                .userIds(usersIds)
                .build())

        then: 'users were removed from project'
        assert !projectsService.findById(project.id).team.collect { it.id }
                .containsAll(usersIds)
    }
}
