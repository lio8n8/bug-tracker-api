package com.app.bugtracker.projects

import com.app.bugtracker.auth.services.IAuthContext
import com.app.bugtracker.projects.repositories.IProjectsRepository
import com.app.bugtracker.projects.services.ProjectsService
import com.app.bugtracker.projects.models.Project
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import java.time.LocalDateTime

import static com.app.bugtracker.Utils.getCreateProjectRequest
import static com.app.bugtracker.Utils.getProject
import static com.app.bugtracker.Utils.getProjects
import static com.app.bugtracker.Utils.getUser

/**
 * Unit tests for Projects service.
 */
class ProjectsServiceUnitTest extends Specification {
    def 'find project by id'() {
        given: 'projects repository mock'
        def projectsRepositoryMock = Mock(IProjectsRepository)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'project service'
        def projectsService = new ProjectsService(
                projectsRepositoryMock,
                authContextMock
        )

        and: 'project'
        def project = getProject()

        when: 'find project by id'
        projectsService.findById(project.id)

        then: 'project returned'
        1 * projectsRepositoryMock.findById(project.id) >> Optional.of(project)
    }

    def 'find all projects'() {
        given: 'projects repository mock'
        def projectsRepositoryMock = Mock(IProjectsRepository)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'project service'
        def projectsService = new ProjectsService(
                projectsRepositoryMock,
                authContextMock
        )

        and: 'page request'
        def request = PageRequest.of(0, 42)

        when: 'find all projects'
        projectsService.findAll(request)

        then: 'findAll method called'
        1 * projectsRepositoryMock.findAll(request)
    }

    def 'create project'() {
        given: 'projects repository mock'
        def projectsRepositoryMock = Mock(IProjectsRepository)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'project service'
        def projectsService = new ProjectsService(
                projectsRepositoryMock,
                authContextMock
        )

        and: 'user'
        def user = getUser()

        and: 'create project request'
        def request = getCreateProjectRequest()

        when: 'create project'
        projectsService.create(request)

        then: 'user authenticated'
        1 * authContextMock.getUser() >> user

        and: 'save method called'
        1 * projectsRepositoryMock.save(!null as Project)
    }

    def 'update project'() {
        given: 'projects repository mock'
        def projectsRepositoryMock = Mock(IProjectsRepository)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'project service'
        def projectsService = new ProjectsService(
                projectsRepositoryMock,
                authContextMock
        )

        and: 'user'
        def user = getUser()

        and: 'project'
        def project = getProject()

        and: 'update project request'
        def request = getCreateProjectRequest()

        when: 'update project'
        projectsService.update(project.id, request)

        then: 'project exists'
        1 * projectsRepositoryMock.findById(project.id) >> Optional.of(project)

        and: 'user authenticated'
        1 * authContextMock.getUser() >> user

        and: 'method save called'
        1 * projectsRepositoryMock.save(!null as Project)
    }

    def 'delete project by id'() {
        given: 'projects repository mock'
        def projectsRepositoryMock = Mock(IProjectsRepository)

        and: 'auth context service mock'
        def authContextMock = Mock(IAuthContext)

        and: 'project service'
        def projectsService = new ProjectsService(
                projectsRepositoryMock,
                authContextMock
        )

        and: 'project exists'
        def project = getProject()

        when: 'delete project by id'
        projectsService.deleteById(project.id)

        then: 'deleteById method called'
        1 * projectsRepositoryMock.deleteById(project.id)
    }
}
