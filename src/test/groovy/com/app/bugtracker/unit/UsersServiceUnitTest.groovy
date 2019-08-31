package com.app.bugtracker.unit

import com.github.javafaker.Faker
import spock.lang.Specification

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import java.util.UUID

import com.app.bugtracker.dto.user.CreateUserDTO;
import com.app.bugtracker.dto.user.UpdateUserDTO;
import com.app.bugtracker.models.User
import com.app.bugtracker.repositories.IUsersRepository
import com.app.bugtracker.services.user.IUsersService
import com.app.bugtracker.services.user.UsersService

class UsersUnitTest extends Specification {
    private Faker faker = new Faker()
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder()
    private IUsersRepository usersRepositoryMock = Mock(IUsersRepository.class)
    private IUsersService usersService = new UsersService(usersRepositoryMock, bCryptPasswordEncoder)

    def 'create a new user' () {
        given: 'a valid user\'s DTO'
        def psw = faker.internet().password()
        def email = faker.internet().emailAddress()
        def createUserDTO = CreateUserDTO.builder()
                .email(email)
                .psw(psw)
                .confirmPsw(psw)
                .build();

        and: 'a user'
        def id = UUID.randomUUID()
        User user = User.builder()
                .id(id)
                .email(createUserDTO.getEmail())
                .build()

        and: 'user\'s info is saved'
        usersRepositoryMock.save(_ as User) >> user

        when: 'user is created'
        def res = usersService.create(createUserDTO)

        then: 'user should be saved'
        res && res.id == id && res.email == email
    }

    def 'get user by id' () {
        given: 'a user'
        def user = this.createUser()
        and: 'a user is exists'
        usersRepositoryMock.findById(user.id) >> Optional.of(user)

        when: 'getting user by id'
        def res = usersService.findById(user.id)

        then: 'user is correct'
        res.id == user.id && res.email == user.email
    }

    def 'update user' () {
        given: 'a user'
        def user = this.createUser()
        and: 'update user DTO'
        def updateUserDTO = UpdateUserDTO.builder()
                .email(user.email)
                .firstName(user.firstName)
                .lastName(user.lastName)
                .build()

        when: 'user is updated'
        usersRepositoryMock.findById(user.id) >> Optional.of(user)
        usersRepositoryMock.save(_ as User) >> user
        def res = usersService.update(user.id, updateUserDTO)
        
        then: 'user should be updated'
        res.id == user.id && res.email == user.email
    }

    def createUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .email(faker.internet().emailAddress())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build()
    }
}
