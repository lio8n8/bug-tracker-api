package com.app.bugtracker.unit

import com.github.javafaker.Faker
import spock.lang.Specification

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import java.util.UUID

import com.app.bugtracker.DTO.user.CreateUserDTO;
import com.app.bugtracker.models.User
import com.app.bugtracker.repositories.IUsersRepository
import com.app.bugtracker.services.user.IUsersService
import com.app.bugtracker.services.user.UsersService

class UsersUnitTest extends Specification {
    private Faker faker = new Faker()
    private IUsersRepository usersRepositoryMock = Mock(IUsersRepository.class)
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder()
    private IUsersService usersService = new UsersService(usersRepositoryMock, bCryptPasswordEncoder)

    def 'should create a new user' () {
        given: 'a valid user\'s data'
        def psw = faker.internet().password()
        def email = faker.internet().emailAddress()
        def createUserDTO = CreateUserDTO.builder()
                .email(email)
                .psw(psw)
                .confirmPsw(psw)
                .build();

        and: 'a user object'
        def id = UUID.randomUUID()
        User user = User.builder()
                .id(id)
                .email(createUserDTO.getEmail())
                .build()
                
        and: 'user\'s info is saved'
        usersRepositoryMock.save(_ as User) >> user

        when: 'user is created'
        def res = usersService.create(createUserDTO)

        then: 'data should be correct'
        res && res.id == id && res.email == email
    }
}
