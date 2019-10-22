package com.app.bugtracker.integration

import com.github.javafaker.Faker
import com.app.bugtracker.dto.user.CreateUserDTO
import com.app.bugtracker.dto.user.UserDTO
import com.app.bugtracker.dto.user.UpdateUserDTO
import com.app.bugtracker.repositories.IUsersRepository
import com.app.bugtracker.models.user.User
import com.app.bugtracker.models.user.UserRoles
import com.app.bugtracker.constants.Urls

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.hateoas.PagedResources
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Shared

import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpMethod.PUT
import static org.springframework.http.HttpMethod.DELETE

class UsersControllerIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder

    @Autowired
    private IUsersRepository usersRepository

    @Shared
    private Faker faker = new Faker()

    final private USERS_URL = BASE_URL + Urls.USERS

    def 'Get user by id'() {
        given: 'A user'
        def user = this.createUser()

        when: 'Finds user by id'
        def result = restTemplate.exchange(USERS_URL + '/' + user.id, GET,
                new HttpEntity<>(TestUtils.getAuthHttpHeaders(user.email)), User.class)

        then: 'It should return a user'
        result.statusCode == HttpStatus.OK && result.body.id == user.id
    }

    def 'Get users'() {
        given: 'A user with admin role'
        def user = usersRepository.save(User.builder()
                .email(faker.internet().emailAddress())
                .psw(bCryptPasswordEncoder.encode(faker.internet().password()))
                .roles([UserRoles.ADMIN] as Set)
                .build())
        def skip = 0
        def limit = 100

        when: 'Finds all users'
        def result = restTemplate.exchange("${USERS_URL}?skip=${skip}&limit=${limit}", GET,
                new HttpEntity<>(TestUtils.getAuthHttpHeaders(user.email)),
                new ParameterizedTypeReference<PagedResources<User>>() {})

        then: 'It should return a list of users'
        result.statusCode == HttpStatus.OK &&
                !result.body.content.isEmpty() &&
                result.body.content.contains(user)
    }

    def 'Create user'() {
        given: 'CreateUserDTO'
        def psw = faker.internet().password()
        def userDTO = CreateUserDTO.builder()
                .email(faker.internet().emailAddress())
                .psw(psw)
                .confirmPsw(psw)
                .build()
        def request = new HttpEntity<>(userDTO, new HttpHeaders());

        when: 'Save user'
        def result = restTemplate.exchange(USERS_URL, POST, request, User.class)

        then: 'It should create user'
        result.statusCode == HttpStatus.CREATED &&
                result.body.id && result.body.email == userDTO.email
    }

    def 'Update user'() {
        given: 'A user'
        def user = this.createUser()
        and: 'UpdateUserDTO'
        def userDTO = UpdateUserDTO.builder()
                .email(faker.internet().emailAddress())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build()

        HttpEntity<User> request = new HttpEntity<>(userDTO, TestUtils.getAuthHttpHeaders(user.email))

        when: 'Save updated user'
        def result = restTemplate.exchange(USERS_URL + '/' + user.id, PUT, request, User.class)

        then: 'It should update user'
        result.statusCode == HttpStatus.OK &&
                result.body.firstName == userDTO.firstName &&
                result.body.lastName == userDTO.lastName
    }

    def 'Delete user' () {
        given: 'A user'
        def user = this.createUser()

        when: 'Delete user'
        def result = restTemplate.exchange(USERS_URL + '/' + user.id, DELETE,
                new HttpEntity<>(TestUtils.getAuthHttpHeaders(user.email)), User.class)

        then: 'Response should be empty'
        result.statusCode == HttpStatus.NO_CONTENT
    }

    def private createUser() {
        return usersRepository.save(User.builder()
                .email(faker.internet().emailAddress())
                .psw(bCryptPasswordEncoder.encode(faker.internet().password()))
                .build())
    }
}
