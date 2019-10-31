package com.app.bugtracker.integration

import com.app.bugtracker.services.auth.IJwtTokenService
import com.app.bugtracker.dto.user.CreateUserDTO
import com.app.bugtracker.dto.user.UserDTO
import com.app.bugtracker.dto.user.UpdateUserDTO
import com.app.bugtracker.repositories.IUsersRepository
import com.app.bugtracker.models.user.User
import com.app.bugtracker.models.user.UserRoles
import com.app.bugtracker.constants.Urls

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.PagedResources
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.reactive.function.BodyInserters

import static org.springframework.http.HttpHeaders.AUTHORIZATION

class UsersControllerIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder

    @Autowired
    private IUsersRepository usersRepository

    @Autowired
    IJwtTokenService tokenService

    def 'get user by id'() {

        given: 'a user'
        def user = this.createUser()

        when: 'find user by id'
        webTestClient.get()
                .uri("${Urls.USERS}/${user.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${tokenService.createToken(user.email)}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserDTO.class)
                .consumeWith({ u ->
                    assert u.responseBody.id == user.id
                    assert u.responseBody.email == user.email
        })

        then: 'success'
        true
    }

    def 'get users'() {

        given: 'a user with admin role'
        def user = usersRepository.save(User.builder()
                .email(TestUtils.faker.internet().emailAddress())
                .psw(bCryptPasswordEncoder
                    .encode(TestUtils.faker.internet().password()))
                .roles([UserRoles.ADMIN] as Set)
                .build())

        when: 'find all users'
        webTestClient.get()
                .uri("${Urls.USERS}?skip=0&limit=100")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${tokenService.createToken(user.email)}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(PagedResources.class)
                .consumeWith({ p ->
                    assert p.responseBody.content
                })

        then: 'success'
        true
    }

    def 'create user'() {

        given: 'create user DTO'
        def psw = TestUtils.faker.internet().password()
        def userDTO = CreateUserDTO
                .builder()
                .email(TestUtils.faker.internet().emailAddress())
                .psw(psw)
                .confirmPsw(psw)
                .build()

        when: 'create user'
        webTestClient.post()
                .uri(Urls.USERS)
                .body(BodyInserters.fromObject(userDTO))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(UserDTO.class)
                .consumeWith({ user ->
                    assert user.responseBody.id
                    assert user.responseBody.email == userDTO.email
                })

        then: 'success'
        true
    }

    def 'update user'() {

        given: 'a user'
        def user = this.createUser()

        and: 'an update user DTO'
        def userDTO = UpdateUserDTO.builder()
                .email(TestUtils.faker.internet().emailAddress())
                .firstName(TestUtils.faker.name().firstName())
                .lastName(TestUtils.faker.name().lastName())
                .build()

        when: 'update user'
        webTestClient.put()
                .uri("${Urls.USERS}/${user.id}")
                .body(BodyInserters.fromObject(userDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer ${tokenService.createToken(user.email)}")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserDTO.class)
                .consumeWith({ u ->
                    assert u.responseBody.id == user.id
                    assert u.responseBody.email == userDTO.email
                    assert u.responseBody.firstName == userDTO.firstName
                    assert u.responseBody.lastName == userDTO.lastName
                })

        then: 'success'
        true
    }

    def 'delete user' () {

        given: 'a user'
        def user = this.createUser()

        when: 'delete user'
        webTestClient.delete()
                .uri("${Urls.USERS}/${user.id}")
                .header(AUTHORIZATION, "Bearer ${tokenService.createToken(user.email)}")
                .exchange()
                .expectStatus()
                .isNoContent()

        then: 'success'
        true
    }

    private User createUser() {
        return usersRepository.save(User.builder()
                .email(TestUtils.faker.internet().emailAddress())
                .psw(bCryptPasswordEncoder.encode(TestUtils.faker.internet().password()))
                .build())
    }
}
