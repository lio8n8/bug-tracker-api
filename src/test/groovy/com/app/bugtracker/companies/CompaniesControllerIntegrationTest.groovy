package com.app.bugtracker.companies

import com.app.bugtracker.BaseControllerIntegrationTest
import com.app.bugtracker.auth.services.ITokensService
import com.app.bugtracker.companies.dto.CompanyDTO
import com.app.bugtracker.companies.services.ICompaniesService
import com.app.bugtracker.users.models.User
import com.app.bugtracker.users.services.IUsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.reactive.function.BodyInserters

import static com.app.bugtracker.Urls.COMPANY
import static com.app.bugtracker.Urls.COMPANIES
import static com.app.bugtracker.Utils.authenticate
import static com.app.bugtracker.Utils.getCreateCompanyRequest
import static com.app.bugtracker.Utils.getCreateUserRequest
import static org.springframework.http.HttpHeaders.AUTHORIZATION
import static org.springframework.http.MediaType.APPLICATION_JSON

/**
 * Integration tests for companies controller.
 */
class CompaniesControllerIntegrationTest extends BaseControllerIntegrationTest {

    @Autowired
    ICompaniesService companiesService

    @Autowired
    IUsersService usersService

    @Autowired
    ITokensService tokensService

    private User user = null

    def setup() {
        user = usersService.create(getCreateUserRequest())
        authenticate(user)
    }

    def cleanup() {

    }

    def 'find company by id'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'company created'
        def company = companiesService.create(getCreateCompanyRequest())

        expect: 'find company by id'
        webTestClient.get()
                .uri(COMPANY, company.id)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CompanyDTO)
                .consumeWith({ c ->
                    assert c.responseBody.id == company.id
                    assert c.responseBody.name == company.name
                    assert c.responseBody.title == company.title
                    assert c.responseBody.description == company.description
                })
    }

    def 'find all companies'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'company created'
        def company = companiesService.create(getCreateCompanyRequest())

        expect: 'find all companies'
        webTestClient.get()
                .uri(COMPANIES)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
    }

    def 'create company'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'create company request'
        def request = getCreateCompanyRequest()

        expect: 'create company'
        webTestClient.post()
                .uri(COMPANIES)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(CompanyDTO)
                .consumeWith({ c ->
                    assert c.responseBody.id
                    assert c.responseBody.name == request.name
                    assert c.responseBody.title == request.title
                    assert c.responseBody.description == request.description
                })
    }

    def 'update company'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'company created'
        def company = companiesService.create(getCreateCompanyRequest())

        and: 'update company request'
        def request = getCreateCompanyRequest()

        expect: 'update company'
        webTestClient.put()
                .uri(COMPANY, company.id)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CompanyDTO)
                .consumeWith({ c ->
                    assert c.responseBody.id == company.id
                    assert c.responseBody.name == request.name
                    assert c.responseBody.title == request.title
                    assert c.responseBody.description == request.description
                })
    }

    def 'delete company by id'() {
        given: 'token'
        def token = tokensService.createToken(user.username)

        and: 'company created'
        def company = companiesService.create(getCreateCompanyRequest())

        expect: 'delete companies'
        webTestClient.delete()
                .uri(COMPANY, company.id)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isNoContent()
    }
}
