package com.app.bugtracker.tokens

import com.app.bugtracker.BaseAcceptanceTest
import com.app.bugtracker.dto.AuthResponseDTO
import com.app.bugtracker.dto.UserDTO
import com.app.bugtracker.dto.UserLoginRequest
import com.app.bugtracker.services.users.IUsersService
import com.app.bugtracker.services.tokens.ITokensService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.reactive.function.BodyInserters

import static com.app.bugtracker.Urls.USER_CURRENT
import static com.app.bugtracker.Utils.getCreateUserRequest
import static com.app.bugtracker.Urls.TOKENS
import static org.springframework.http.HttpHeaders.AUTHORIZATION
import static org.springframework.http.MediaType.APPLICATION_JSON

class AuthControllerAcceptanceTest extends BaseAcceptanceTest {

    @Autowired
    private IUsersService usersService

    @Autowired
    private ITokensService tokensService

    def 'get token'() {

        given: 'create user request'
        def createUserReq = getCreateUserRequest()

        and: 'user created'
        usersService.create(createUserReq)

        and: 'user login request'
        def request = UserLoginRequest.builder()
                .username(createUserReq.username)
                .password(createUserReq.password)
                .build()

        when: 'get token'
        webTestClient.post()
                .uri(TOKENS)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AuthResponseDTO)
                .consumeWith({ res ->
                    assert res.responseBody.token
                })

        then: 'success'
        true
    }

    def 'get current user'() {

        given: 'create user request'
        def createUserReq = getCreateUserRequest()

        and: 'user created'
        def user = usersService.create(createUserReq)

        and: 'get token'
        def token = tokensService.createToken(createUserReq.username)

        when: 'find user by id'
        webTestClient.get()
                .uri(USER_CURRENT)
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
}
