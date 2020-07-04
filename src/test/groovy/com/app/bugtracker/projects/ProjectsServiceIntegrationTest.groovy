package com.app.bugtracker.projects

import com.app.bugtracker.BaseServiceIntegrationTest
import com.app.bugtracker.projects.repositories.IProjectsRepository
import com.app.bugtracker.projects.services.IProjectsService
import com.app.bugtracker.users.models.User
import com.app.bugtracker.users.services.IUsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest

import static com.app.bugtracker.Utils.authenticate
import static com.app.bugtracker.Utils.getCreateProjectRequest
import static com.app.bugtracker.Utils.getCreateUserRequest

/**
 * Integrations tests for Projects service.
 */
class ProjectsServiceIntegrationTest extends BaseServiceIntegrationTest {

    @Autowired
    IProjectsService projectsService

    @Autowired
    IUsersService usersService

    @Autowired
    IProjectsRepository projectsRepository

    private User user = null

    def setup() {
        user = usersService.create(getCreateUserRequest())
        authenticate(user)
    }

    def 'find project by id'() {
        given: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        when: 'find project by id'
        def res = projectsService.findById(project.id)

        then: 'project returned'
        with(res) {
            id == project.id
            title == project.title
            description == project.description
        }
    }

    def 'find all projects'() {
        given: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        when: 'find all tasks'
        def res = projectsService.findAll(PageRequest.of(0, 50))

        then: 'page with project returned'
        assert res.getContent().collect { it.id }.contains(project.id)
    }

    def 'create project'() {
        given: 'create project request'
        def request = getCreateProjectRequest()

        when: 'create project'
        def res = projectsService.create(request)

        then: 'project created'
        with(res) {
            assert id
            title == request.title
            description == request.description
            createdBy.id == user.id
            updatedBy.id == user.id
            assert createdAt
            assert updatedAt
        }
    }

    def 'update project'() {
        given: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        and: 'update project request'
        def request = getCreateProjectRequest()

        when: 'update project'
        def res = projectsService.update(project.id, request)

        then: 'project updated'
        with(res) {
            id == project.id
            title == request.title
            description == request.description
            updatedBy.id == user.id
            updatedAt.isAfter(project.updatedAt)
        }
    }

    def 'delete project by id'() {
        given: 'project created'
        def project = projectsService.create(getCreateProjectRequest())

        when: 'delete project by id'
        projectsService.deleteById(project.id)

        then: 'project deleted'
        projectsRepository.findById(project.id).isEmpty()
    }
}
