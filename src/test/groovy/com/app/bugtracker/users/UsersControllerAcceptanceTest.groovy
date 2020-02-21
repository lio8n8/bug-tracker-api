package com.app.bugtracker.users

import com.app.bugtracker.BaseAcceptanceTest
import com.app.bugtracker.dto.UserDTO
import com.app.bugtracker.dto.UserUpdateRequest
import com.app.bugtracker.services.users.IUsersService
import com.app.bugtracker.services.tokens.ITokensService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.web.reactive.function.BodyInserters

import static com.app.bugtracker.Utils.faker
import static com.app.bugtracker.Utils.getCreateUserRequest
import static com.app.bugtracker.Urls.USER
import static com.app.bugtracker.Urls.USERS
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.http.HttpHeaders.AUTHORIZATION

class UsersControllerAcceptanceTest extends BaseAcceptanceTest {

    @Autowired
    private IUsersService usersService

    @Autowired
    private ITokensService tokensService

    def 'find user by id'() {
        given: 'create user request'
        def createUserReq = getCreateUserRequest()

        and: 'user created'
        def user = usersService.create(createUserReq)

        and: 'token'
        def token = tokensService.createToken(user.username)

        when: 'find user by id'
        webTestClient.get()
                .uri(USER, user.id)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserDTO.class)
                .consumeWith({ u ->
                    assert u.responseBody.id == user.id
                    assert u.responseBody.username == user.username
                })

        then: 'success'
        true
    }

    def 'get all users'() {
        given: 'create user request'
        def createUserReq = getCreateUserRequest()

        and: 'user created'
        def user = usersService.create(createUserReq)

        and: 'token'
        def token = tokensService.createToken(user.username)

        when: 'find all users'
        webTestClient.get()
                .uri(USERS, PageRequest.of(0, 25))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Page.class)
                .consumeWith({ page ->
                    assert page.responseBody.content
                })

        then: 'success'
        true
    }

    def 'create user'() {
        given: 'create user request'
        def request = getCreateUserRequest()

        when: 'find all users'
        webTestClient.post()
                .uri(USERS)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(UserDTO)
                .consumeWith({ user ->
                    assert user.responseBody.id
                    assert user.responseBody.username == request.username
                    assert user.responseBody.email == request.email
                    assert user.responseBody.firstName == request.firstName
                    assert user.responseBody.lastName == request.lastName
                })

        then: 'success'
        true
    }

    def 'update user'() {
        given: 'create user request'
        def createUserReq = getCreateUserRequest()

        and: 'user created'
        def user = usersService.create(createUserReq)

        and: 'token'
        def token = tokensService.createToken(user.username)

        and: 'update user request'
        def request = UserUpdateRequest.builder()
                .email(faker.internet().emailAddress())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build()

        when: 'update user'
        webTestClient.put()
                .uri(USER, user.id)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserDTO.class)
                .consumeWith({ u ->
                    assert u.responseBody.id == user.id
                    assert u.responseBody.email == request.email
                    assert u.responseBody.firstName == request.firstName
                    assert u.responseBody.lastName == request.lastName
                })

        then: 'success'
        true
    }

    def 'delete user by id'() {
        given: 'create user request'
        def createUserReq = getCreateUserRequest()

        and: 'user created'
        def user = usersService.create(createUserReq)

        and: 'token'
        def token = tokensService.createToken(user.username)

        when: 'update user'
        webTestClient.delete()
                .uri(USER, user.id)
                .header(AUTHORIZATION, "Bearer ${token}")
                .exchange()
                .expectStatus()
                .isNoContent()

        then: 'success'
        true
    }
}