package com.app.bugtracker.users

import com.app.bugtracker.dto.UserCreateRequest
import com.app.bugtracker.dto.UserUpdateRequest
import com.app.bugtracker.models.User
import com.app.bugtracker.repositories.IUsersRepository
import com.app.bugtracker.services.users.UsersService
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

import static com.app.bugtracker.Utils.faker
import static com.app.bugtracker.Utils.getUser

class UsersServiceUnitTest extends Specification {

    def 'find all users'() {

        given: 'users repository mock'
        def usersRepositoryMock = Mock(IUsersRepository)

        and: 'BCrypt password encoder'
        def bCryptPasswordEncoder = Mock(BCryptPasswordEncoder)

        and:'users service'
        def usersService = new UsersService(usersRepositoryMock, bCryptPasswordEncoder)

        and: 'page request'
        PageRequest request = PageRequest.of(0, 25)

        when: 'get users'
        usersService.findAll(request)

        then: 'find all method called'
        1 * usersRepositoryMock.findAll(request)
    }

    def 'find user by id'() {

        given: 'users repository mock'
        def usersRepositoryMock = Mock(IUsersRepository)

        and: 'BCrypt password encoder'
        def bCryptPasswordEncoder = Mock(BCryptPasswordEncoder)

        and:'users service'
        def usersService = new UsersService(usersRepositoryMock, bCryptPasswordEncoder)

        and: 'the user'
        def user = getUser()

        when: 'find user by id'
        def findedUser = usersService.findById(user.id)

        then: 'find by id method was called'
        1 * usersRepositoryMock.findById(user.id) >> Optional.of(user)

        and: 'user returned'
        findedUser && findedUser.id && findedUser.id == user.id
    }

    def 'find user by username'() {

        given: 'users repository mock'
        def usersRepositoryMock = Mock(IUsersRepository)

        and: 'BCrypt password encoder'
        def bCryptPasswordEncoder = Mock(BCryptPasswordEncoder)

        and:'users service'
        def usersService = new UsersService(usersRepositoryMock, bCryptPasswordEncoder)

        and: 'the user'
        def user = getUser()

        when: 'find user by username'
        def findedUser = usersService.findByUsername(user.username)

        then: 'find by username method was called'
        1 * usersRepositoryMock.findByUsername(user.username) >> Optional.of(user)

        and: 'user returned'
        findedUser && findedUser.id && findedUser.username == user.username
    }

    def 'find user by email'() {

        given: 'users repository mock'
        def usersRepositoryMock = Mock(IUsersRepository)

        and: 'BCrypt password encoder'
        def bCryptPasswordEncoder = Mock(BCryptPasswordEncoder)

        and:'users service'
        def usersService = new UsersService(usersRepositoryMock, bCryptPasswordEncoder)

        and: 'the user'
        def user = getUser()

        when: 'find user by email'
        def findedUser = usersService.findByEmail(user.email)

        then: 'find by email method was called'
        1 * usersRepositoryMock.findByEmail(user.email) >> Optional.of(user)

        and: 'user returned'
        findedUser && findedUser.id && findedUser.email == user.email
    }

    def 'create user'() {

        given: 'users repository mock'
        def usersRepositoryMock = Mock(IUsersRepository)

        and: 'BCrypt password encoder'
        def bCryptPasswordEncoder = Mock(BCryptPasswordEncoder)

        and:'users service'
        def usersService = new UsersService(usersRepositoryMock, bCryptPasswordEncoder)

        and: 'create user request'
        def psw = faker.internet().password()
        def request = UserCreateRequest.builder()
                .email(faker.internet().emailAddress())
                .username(faker.name().username())
                .password(psw)
                .confirmPassword(psw)
                .build()

        and: 'the user'
        def user = User.builder()
                .id(UUID.randomUUID())
                .username(request.username)
                .email(request.email)
                .psw(faker.internet().password())
                .build()

        when: 'create user'
        def createdUser = usersService.create(request)

        then: 'password encoded'
        1 * bCryptPasswordEncoder.encode(request.password)

        and: 'repository save method was called'
        1 * usersRepositoryMock.save(!null as User) >> user

        and: 'user data returned'
        with (createdUser) {
            id == user.id
            email == user.email
            username == user.username
        }
    }

    def 'create user with wrong confirm password'() {
        // TODO: Add implementation.
    }

    def 'create user with invalid email'() {
        // TODO: Add implementation.
    }

    def 'update user'() {

        given: 'users repository mock'
        def usersRepositoryMock = Mock(IUsersRepository)

        and: 'BCrypt password encoder'
        def bCryptPasswordEncoder = Mock(BCryptPasswordEncoder)

        and:'users service'
        def usersService = new UsersService(usersRepositoryMock, bCryptPasswordEncoder)

        and: 'the user'
        def user = getUser()

        and: 'update user request'
        def request = UserUpdateRequest.builder()
                .email(faker.internet().emailAddress())
                .build()

        when: 'update user'
        def updatedUser = usersService.update(user.id, request)

        then: 'user exists'
        1 * usersRepositoryMock.findById(user.id) >> Optional.of(user)

        then: 'method save was called'
        user.setEmail(request.email)
        1 * usersRepositoryMock.save(!null as User) >> user

        and: 'updated user returned'
        updatedUser && updatedUser.email == request.email
    }

    def 'delete user' () {

        given: 'users repository mock'
        def usersRepositoryMock = Mock(IUsersRepository)

        and: 'BCrypt password encoder'
        def bCryptPasswordEncoder = Mock(BCryptPasswordEncoder)

        and:'users service'
        def usersService = new UsersService(usersRepositoryMock, bCryptPasswordEncoder)

        and: 'the user'
        def user = getUser()

        when: 'delete user by id'
        usersService.deleteById(user.id)

        then: 'delete user by id method was called'
        1 * usersRepositoryMock.deleteById(user.id)
    }
}
