package com.app.bugtracker.projects

import com.app.bugtracker.projects.dto.ProjectDTO
import com.app.bugtracker.projects.services.IProjectsService
import com.app.bugtracker.projects.controllers.ProjectsController
import com.app.bugtracker.tasks.dto.TaskDTO
import com.app.bugtracker.projects.models.Project
import com.app.bugtracker.tasks.dto.TaskRequest
import com.app.bugtracker.tasks.models.Priority
import com.app.bugtracker.tasks.models.Status
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import static com.app.bugtracker.Utils.getCreateProjectRequest
import static com.app.bugtracker.Utils.getProject
import static com.app.bugtracker.Utils.getProjects
import static com.app.bugtracker.Utils.getTask
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

/**
 * Unit tests for Projects controller.
 */
class ProjectsControllerUnitTest extends Specification {
    def 'find project by id'() {
        given: 'projects service mock'
        def projectsServiceMock = Mock(IProjectsService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'projects controller'
        def projectsController = new ProjectsController(
                projectsServiceMock,
                conversionServiceMock
        )

        and: 'project'
        def project = getProject()

        when: 'find project by id'
        def res = projectsController.findById(project.id)

        then: 'project returned'
        1 * projectsServiceMock.findById(project.id) >> project

        and: 'conversion service called'
        1 *  conversionServiceMock.convert(project, ProjectDTO) >> ProjectDTO.builder()
                .id(task.id)
                .title(task.title)
                .description(task.description)
                .build()

        and: 'response OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'find all projects'() {
        given: 'projects service mock'
        def projectsServiceMock = Mock(IProjectsService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'projects controller'
        def projectsController = new ProjectsController(
                projectsServiceMock,
                conversionServiceMock
        )

        and: 'projects'
        def projects = getProjects()

        and: 'page'
        def page = new PageImpl<>(projects)

        and: 'page request'
        def request = PageRequest.of(0, 25)

        when: 'find all projects'
        def res = projectsController.findAll(request)

        then: 'page was returned'
        1 * projectsServiceMock.findAll(request) >> page

        and: 'conversion service called'
        projects.size() * conversionServiceMock.convert(!null as Project, ProjectDTO)

        and: 'response OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'create project'() {
        given: 'projects service mock'
        def projectsServiceMock = Mock(IProjectsService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'projects controller'
        def projectsController = new ProjectsController(
                projectsServiceMock,
                conversionServiceMock
        )

        and: 'project'
        def project = getProject()

        and: 'create project request'
        def request = getCreateProjectRequest()

        when: 'create project'
        def res = projectsController.create(request)

        then: 'project created'
        1 * projectsServiceMock.create(request) >> project

        and: 'conversion service called'
        1 *  conversionServiceMock.convert(project, ProjectDTO) >> ProjectDTO.builder()
                .id(project.id)
                .title(project.title)
                .description(project.description)
                .build()

        and: 'response status is CREATED'
        res.statusCode == CREATED

        and: 'response contains body'
        res.body
    }

    def 'update project'() {
        given: 'projects service mock'
        def projectsServiceMock = Mock(IProjectsService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'projects controller'
        def projectsController = new ProjectsController(
                projectsServiceMock,
                conversionServiceMock
        )

        and: 'project'
        def project = getProject()

        and: 'update project request'
        def request = getCreateProjectRequest()

        when: 'update project'
        def res = projectsController.update(project.id, request)

        then: 'project updated'
        1 * projectsServiceMock.update(project.id, request) >> project.tap {
            title = request.title
            description = request.description
        }

        and: 'conversion service called'
        1 * conversionServiceMock.convert(project, ProjectDTO) >> ProjectDTO.builder()
                .id(project.id)
                .title(request.title)
                .description(request.description)
                .build()

        and: 'response status is OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'delete project by id'() {
        given: 'projects service mock'
        def projectsServiceMock = Mock(IProjectsService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'projects controller'
        def projectsController = new ProjectsController(
                projectsServiceMock,
                conversionServiceMock
        )

        and: 'project'
        def project = getProject()

        when: 'delete project by id'
        def res = projectsController.deleteById(project.id)

        then: 'project is deleted'
        1 * projectsServiceMock.deleteById(project.id)

        and: 'response status is NO_CONTENT'
        res.statusCode == NO_CONTENT
    }
}
