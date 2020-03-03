package com.app.bugtracker.companies

import com.app.bugtracker.BaseServiceIntegrationTest
import com.app.bugtracker.companies.repositories.ICompaniesRepository
import com.app.bugtracker.companies.services.ICompaniesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest

import static com.app.bugtracker.Utils.getCreateCompanyRequest

/**
 * Integrations tests for companies service.
 */
class CompaniesServiceIntegrationTest extends BaseServiceIntegrationTest {

    @Autowired
    ICompaniesService companiesService

    @Autowired
    ICompaniesRepository companiesRepository

    def setup() {

    }

    def cleanup() {

    }

    def 'find company by id'() {
        given: 'company created'
        def company = companiesService.create(getCreateCompanyRequest())

        when: 'find companies by id'
        def res = companiesService.findById(company.id)

        then: 'company returned'
        with(res) {
            id == company.id
            name == company.name
            title == company.title
            description == company.description
        }
    }

    def 'find all companies'() {
        given: 'company created'
        def company = companiesService.create(getCreateCompanyRequest())

        when: 'find all companies'
        def res = companiesService.findAll(PageRequest.of(0, 25))

        then: 'page with created company returned'
        assert res.getContent().any { it.id == company.id}
    }

    def 'create a new company'() {
        given: 'create company request'
        def request = getCreateCompanyRequest()

        when: 'create company'
        def res = companiesService.create(request)

        then: 'company created'
        with(res) {
            assert id
            name == request.name
            title == request.title
            description == request.description
        }
    }

    def 'update company'() {
        given: 'company created'
        def company = companiesService.create(getCreateCompanyRequest())

        and: 'update company request'
        def request = getCreateCompanyRequest()

        when: 'update company'
        def res = companiesService.update(company.id, request)

        then: 'company updated'
        with(res) {
            id == company.id
            name == request.name
            title == request.title
            description == request.description
        }
    }

    def 'delete company by id'() {
        given: 'company created'
        def companies = companiesService.create(getCreateCompanyRequest())

        when: 'delete company by id'
        companiesService.deleteById(companies.id)

        then: 'company deleted'
        companiesRepository.findById(companies.id).isEmpty()
    }
}
