package com.app.bugtracker.users

import com.app.bugtracker.users.controllers.UsersController
import com.app.bugtracker.users.dto.UserCreateRequest
import com.app.bugtracker.users.dto.UserDTO
import com.app.bugtracker.users.dto.UserUpdateRequest
import com.app.bugtracker.users.models.User
import com.app.bugtracker.users.services.IUsersService
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import static com.app.bugtracker.Utils.faker
import static com.app.bugtracker.Utils.getUser
import static com.app.bugtracker.Utils.getUsers
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NO_CONTENT

class UsersControllerUnitTest extends Specification {
    def 'find all users'() {
        given: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'users controller'
        def usersController = new UsersController(
                usersServiceMock,
                conversionServiceMock
        )

        and: 'users'
        def users = getUsers()

        and: 'page'
        def page = new PageImpl<>(users)

        and: 'page request'
        def request = PageRequest.of(0, 25)

        when: 'find all users'
        def res = usersController.findAll(request)

        then: 'page was returned'
        1 * usersServiceMock.findAll(request) >> page

        and: 'conversion service called'
        users.size() * conversionServiceMock.convert(!null as User, UserDTO)

        and: 'response OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'find user by id'() {
        given: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'users controller'
        def usersController = new UsersController(
                usersServiceMock,
                conversionServiceMock
        )

        and: 'user'
        def user = getUser()

        when: 'find user by id'
        def res = usersController.findById(user.id)

        then: 'user returned'
        1 * usersServiceMock.findById(user.id) >> user

        and: 'conversion service called'
        1 *  conversionServiceMock.convert(user, UserDTO) >> UserDTO.builder()
                .id(user.id)
                .username(user.username)
                .email(user.email)
                .build()

        and: 'response OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'create user'() {
        given: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'users controller'
        def usersController = new UsersController(
                usersServiceMock,
                conversionServiceMock
        )

        and: 'user'
        def user = getUser()

        and: 'create user request'
        def psw = faker.internet().password()
        def request = UserCreateRequest.builder()
                .username(user.username)
                .email(user.email)
                .password(psw)
                .confirmPassword(psw)
                .build()

        when: 'create user'
        def res = usersController.create(request)

        then: 'user created'
        1 * usersServiceMock.create(request) >> user

        and: 'conversion service called'
        1 *  conversionServiceMock.convert(user, UserDTO) >> UserDTO.builder()
                .id(user.id)
                .username(user.username)
                .email(user.email)
                .build()

        and: 'response status is CREATED'
        res.statusCode == CREATED

        and: 'response contains body'
        res.body
    }

    def 'update user'() {
        given: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'users controller'
        def usersController = new UsersController(
                usersServiceMock,
                conversionServiceMock
        )

        and: 'user'
        def user = getUser()

        and: 'update user request'
        def request = UserUpdateRequest.builder()
                .email(faker.internet().emailAddress())
                .build()

        when: 'update user'
        def res = usersController.update(user.id, request)

        then: 'user updated'
        1 * usersServiceMock.update(user.id, request) >> user.tap {
            email = request.email
        }

        and: 'conversion service called'
        1 * conversionServiceMock.convert(user, UserDTO) >> UserDTO.builder()
                .id(user.id)
                .username(user.username)
                .email(request.email)
                .build()

        and: 'response status is OK'
        res.statusCode == OK

        and: 'response contains body'
        res.body
    }

    def 'delete user by id'() {
        given: 'users service mock'
        def usersServiceMock = Mock(IUsersService)

        and: 'conversion service mock'
        def conversionServiceMock = Mock(ConversionService)

        and: 'users controller'
        def usersController = new UsersController(
                usersServiceMock,
                conversionServiceMock
        )

        and: 'user'
        def user = getUser()

        when: 'delete user by id'
        def res = usersController.delete(user.id)

        then: 'user deleted'
        1 * usersServiceMock.deleteById(user.id)

        and: 'response status is NO_CONTENT'
        res.statusCode == NO_CONTENT
    }
}
