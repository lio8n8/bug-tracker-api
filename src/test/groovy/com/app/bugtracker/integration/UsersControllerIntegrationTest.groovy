package com.app.bugtracker.integration

import com.github.javafaker.Faker
import com.app.bugtracker.dto.user.UserDTO
import com.app.bugtracker.repositories.IUsersRepository
import com.app.bugtracker.services.auth.IJwtTokenService
import com.app.bugtracker.models.User
import com.app.bugtracker.constants.Urls;
import com.app.bugtracker.constants.UserRoles

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static org.springframework.http.HttpMethod.*

class UsersControllerIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate

    @Autowired
    private IJwtTokenService jwtTokenService

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder

    @Autowired
    private IUsersRepository usersRepository

    private Faker faker = new Faker()

    private baseUrl = 'http://127.0.0.1:8080/api' + Urls.USERS

    def 'Get user by id'() {
        given: 'A user'
        def user = usersRepository.save(User.builder()
                .email(faker.internet().emailAddress())
                .psw(bCryptPasswordEncoder.encode(faker.internet().password()))
                .build())
        HttpHeaders headers = new HttpHeaders()
        headers.add(HttpHeaders.AUTHORIZATION, jwtTokenService.createToken(user.email))

        when: 'Finds user by id'
        // def result = restTemplate.getForObject('http://127.0.0.1:8080/api/users/f74ad82f-443b-46ba-adcb-f21a384cb093', User.class)
        def result = restTemplate.exchange(baseUrl + '/' + user.id, GET, new HttpEntity<>(headers), User.class)
        println(result.getBody().getId())
        println(result.getBody().getEmail())

        then: 'It should return a user info'
        result.getBody().id == user.id
        println(user);
    }
}
