package com.app.bugtracker.companies

import com.app.bugtracker.companies.repositories.ICompaniesRepository
import com.app.bugtracker.companies.services.CompaniesService
import com.app.bugtracker.companies.models.Company
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import static com.app.bugtracker.Utils.getCreateCompanyRequest
import static com.app.bugtracker.Utils.getCompany

/**
 * Unit tests for companies service.
 */
class CompaniesServiceUnitTest extends Specification {
    def 'find company by id'() {
        given: 'companies repository mock'
        def companiesRepositoryMock = Mock(ICompaniesRepository)

        and: 'companies service'
        def companiesService = new CompaniesService(
                companiesRepositoryMock
        )

        and: 'company'
        def company = getCompany()

        when: 'find company by id'
        companiesService.findById(company.id)

        then: 'company returned'
        1 * companiesRepositoryMock.findById(company.id) >> Optional.of(company)
    }

    def 'find all companies'() {
        given: 'companies repository mock'
        def companiesRepositoryMock = Mock(ICompaniesRepository)

        and: 'companies service'
        def companiesService = new CompaniesService(
                companiesRepositoryMock
        )

        and: 'page request'
        def request = PageRequest.of(0, 42)

        when: 'find all companies'
        companiesService.findAll(request)

        then: 'findAll method called'
        1 * companiesRepositoryMock.findAll(request)
    }

    def 'create a new company'() {
        given: 'companies repository mock'
        def companiesRepositoryMock = Mock(ICompaniesRepository)

        and: 'companies service'
        def companiesService = new CompaniesService(
                companiesRepositoryMock
        )

        and: 'create company request'
        def request = getCreateCompanyRequest()

        when: 'create company'
        companiesService.create(request)

        then: 'save method called'
        1 * companiesRepositoryMock.save(!null as Company)
    }

    def 'update company'() {
        given: 'companies repository mock'
        def companiesRepositoryMock = Mock(ICompaniesRepository)

        and: 'companies service'
        def companiesService = new CompaniesService(
                companiesRepositoryMock
        )

        and: 'company'
        def company = getCompany()

        and: 'update company request'
        def request = getCreateCompanyRequest()

        when: 'update company'
        companiesService.update(company.id, request)

        then: 'companies exists'
        1 * companiesRepositoryMock.findById(company.id) >> Optional.of(company)

        and: 'method save called'
        1 * companiesRepositoryMock.save(!null as Company)
    }

    def 'delete company by id'() {
        given: 'companies repository mock'
        def companiesRepositoryMock = Mock(ICompaniesRepository)

        and: 'companies service'
        def companiesService = new CompaniesService(
            companiesRepositoryMock
        )

        and: 'company exists'
        def company = getCompany()

        when: 'delete company by id'
        companiesService.deleteById(company.id)

        then: 'deleteById method called'
        1 * companiesRepositoryMock.deleteById(company.id)
    }
}
