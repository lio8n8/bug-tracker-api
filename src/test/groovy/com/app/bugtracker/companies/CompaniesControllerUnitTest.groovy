package com.app.bugtracker.companies

import com.app.bugtracker.companies.dto.CompanyDTO
import com.app.bugtracker.companies.services.ICompaniesService
import com.app.bugtracker.companies.controllers.CompaniesController
import com.app.bugtracker.companies.models.Company
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import static com.app.bugtracker.Utils.getCompany
import static com.app.bugtracker.Utils.getCompanies
import static com.app.bugtracker.Utils.getCreateCompanyRequest
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

/**
 * Unit tests for companies controller.
 */
class CompaniesControllerUnitTest extends Specification {
    def 'find company by id'() {
        given: 'companies service mock'
        def companiesServiceMock = Mock(ICompaniesService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'companies controller'
        def companiesController = new CompaniesController(
                companiesServiceMock,
                conversionServiceMock
        )

        and: 'company'
        def company = getCompany()

        when: 'find company by id'
        def res = companiesController.findById(company.id)

        then: 'company returned'
        1 * companiesServiceMock.findById(company.id) >> company

        and: 'conversion service called'
        1 *  conversionServiceMock.convert(company, CompanyDTO) >> CompanyDTO.builder()
                .id(company.id)
                .name(company.name)
                .title(company.title)
                .description(company.description)
                .build()

        and: 'response OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'find all companies'() {
        given: 'companies service mock'
        def companiesServiceMock = Mock(ICompaniesService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'companies controller'
        def companiesController = new CompaniesController(
                companiesServiceMock,
                conversionServiceMock
        )

        and: 'companies'
        def companies = getCompanies()

        and: 'page'
        def page = new PageImpl<>(companies)

        and: 'page request'
        def request = PageRequest.of(0, 25)

        when: 'find all companies'
        def res = companiesController.findAll(request)

        then: 'page was returned'
        1 * companiesServiceMock.findAll(request) >> page

        and: 'conversion service called'
        companies.size() * conversionServiceMock.convert(!null as Company, CompanyDTO)

        and: 'response OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'create company'() {
        given: 'companies service mock'
        def companiesServiceMock = Mock(ICompaniesService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'companies controller'
        def companiesController = new CompaniesController(
                companiesServiceMock,
                conversionServiceMock
        )

        and: 'create company request'
        def request = getCreateCompanyRequest()

        and: 'company'
        def company = Company.builder()
                .id(UUID.randomUUID())
                .name(request.name)
                .title(request.title)
                .description(request.description)
                .build()

        when: 'create company'
        def res = companiesController.create(request)

        then: 'company created'
        1 * companiesServiceMock.create(request) >> company

        and: 'conversion service called'
        1 *  conversionServiceMock.convert(company, CompanyDTO) >> CompanyDTO.builder()
                .id(company.id)
                .name(request.name)
                .title(request.title)
                .description(request.description)
                .build()

        and: 'response status is CREATED'
        res.statusCode == CREATED

        and: 'response contains body'
        res.body
    }

    def 'update company'() {
        given: 'companies service mock'
        def companiesServiceMock = Mock(ICompaniesService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'companies controller'
        def companiesController = new CompaniesController(
                companiesServiceMock,
                conversionServiceMock
        )

        and: 'company'
        def company = getCompany()

        and: 'update companies request'
        def request = getCreateCompanyRequest()

        when: 'update company'
        def res = companiesController.update(company.id, request)

        then: 'company updated'
        1 * companiesServiceMock.update(company.id, request) >> company

        and: 'conversion service called'
        1 * conversionServiceMock.convert(company, CompanyDTO) >> CompanyDTO.builder()
                .id(company.id)
                .name(request.name)
                .title(request.title)
                .description(request.description)
                .build()

        and: 'response status is OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'delete company by id'() {
        given: 'companies service mock'
        def companiesServiceMock = Mock(ICompaniesService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'companies controller'
        def companiesController = new CompaniesController(
                companiesServiceMock,
                conversionServiceMock
        )

        and: 'company'
        def company = getCompany()

        when: 'delete company by id'
        def res = companiesController.deleteById(company.id)

        then: 'company is deleted'
        1 * companiesServiceMock.deleteById(company.id)

        and: 'response status is NO_CONTENT'
        res.statusCode == NO_CONTENT
    }
}
