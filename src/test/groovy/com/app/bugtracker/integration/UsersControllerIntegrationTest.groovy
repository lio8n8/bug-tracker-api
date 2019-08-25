package com.app.bugtracker.integration

import com.github.javafaker.Faker
import com.app.bugtracker.dto.user.UserDTO
import com.app.bugtracker.repositories.IUsersRepository
import com.app.bugtracker.models.User
import com.app.bugtracker.constants.UserRoles
import com.app.bugtracker.constants.Urls

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Shared

import static org.springframework.http.HttpMethod.GET

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
        def user = usersRepository.save(User.builder()
                .email(faker.internet().emailAddress())
                .psw(bCryptPasswordEncoder.encode(faker.internet().password()))
                .build())

        when: 'Finds user by id'
        def result = restTemplate.exchange(USERS_URL + '/' + user.id, GET,
                new HttpEntity<>(TestUtils.getAuthHttpHeaders(user.email)), User.class)

        then: 'It should return a user info'
        result.getBody().id == user.id
    }
}
