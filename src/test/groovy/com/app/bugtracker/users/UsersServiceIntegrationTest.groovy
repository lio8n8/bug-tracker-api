package com.app.bugtracker.users

import com.app.bugtracker.BaseIntegrationTest
import com.app.bugtracker.dto.UserCreateRequest
import com.app.bugtracker.dto.UserUpdateRequest
import com.app.bugtracker.repositories.IUsersRepository
import com.app.bugtracker.services.users.IUsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest

import static com.app.bugtracker.Utils.faker

public class UsersServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private IUsersRepository usersRepository

    @Autowired
    private IUsersService usersService

    def 'get all users'() {

        given: 'user'
        def user = usersService.create(UserCreateRequest.builder()
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build())

        when: 'find all users'
        def res = usersService.findAll(PageRequest.of(0, 25))

        then: 'page with user returned'
        assert res.getContent().any { it.id == user.id}
    }

    def 'find user by id'() {

        given: 'the user'
        def user = usersService.create(UserCreateRequest.builder()
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build())

        when: 'find user by id'
        def findedUser = usersService.findById(user.id)

        then: 'correct user returned'
        assert findedUser
        assert findedUser.id == user.id
    }

    def 'find user by username'() {

        given: 'the user'
        def user = usersService.create(UserCreateRequest.builder()
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build())

        when: 'find user by username'
        def findedUser = usersService.findByUsername(user.username)

        then: 'correct user returned'
        assert findedUser
        assert findedUser.username == user.username
    }

    def 'find user by email'() {

        given: 'the user'
        def user = usersService.create(UserCreateRequest.builder()
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build())

        when: 'find user by email'
        def findedUser = usersService.findByEmail(user.email)

        then: 'correct user returned'
        assert findedUser
        assert findedUser.email == user.email
    }

    def 'create user'() {

        given: 'create user request'
        def request = UserCreateRequest.builder()
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build()

        when: 'create user'
        def user = usersService.create(request)

        then: 'user created'
        with(user) {
            assert id
            username == request.username
            email == request.email
        }
    }

    def 'create user with wrong confirm password'() {
        // TODO: Add implementation.
    }

    def 'create user with invalid email'() {
        // TODO: Add implementation.
    }

    def 'update user'() {

        given: 'the user'
        def user = usersService.create(UserCreateRequest.builder()
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build())

        and: 'update user request'
        def request = UserUpdateRequest.builder()
                .email(faker.internet().emailAddress())
                .build()

        when: 'update user'
        def updatedUser = usersService.update(user.id, request)

        then: 'user updated'
        with(updatedUser) {
            id == user.id
            email == request.email
        }
    }

    def 'delete user' () {

        given: 'the user'
        def user = usersService.create(UserCreateRequest.builder()
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build())

        when: 'delete user by id'
        usersService.deleteById(user.id)

        then: 'user deleted'
        !usersRepository.findById(user.id)
    }
}
