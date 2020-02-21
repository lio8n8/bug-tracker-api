package com.app.bugtracker.auth

import com.app.bugtracker.auth.dto.UserLoginRequest
import com.app.bugtracker.auth.controllers.AuthController
import com.app.bugtracker.users.dto.UserDTO
import com.app.bugtracker.auth.services.ITokensService
import com.app.bugtracker.auth.services.AuthContext
import org.springframework.core.convert.ConversionService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import spock.lang.Specification

import static com.app.bugtracker.Utils.getRandomWord
import static com.app.bugtracker.Utils.getUser
import static org.springframework.http.HttpStatus.OK

class AuthControllerUnitTest extends Specification {

    def 'get token' () {
        given: 'authentication manager mock'
        def authenticationManagerMock = Mock(AuthenticationManager)

        and: 'tokens service'
        def tokensServiceMock = Mock(ITokensService)

        and: 'conversion service'
        def conversionServiceMock = Mock(ConversionService)

        and: 'auth context service'
        def authContextMock = Mock(AuthContext)

        def authController = new AuthController(
                authenticationManagerMock,
                tokensServiceMock,
                conversionServiceMock,
                authContextMock
        )

        and: 'valid user'
        def user = getUser()

        and: 'authentication request'
        def request = UserLoginRequest.builder()
                .username(user.username)
                .password(user.psw)
                .build()

        when: 'get token'
        def res = authController.getToken(request)

        then: 'user authenticated'
        1 * authenticationManagerMock.authenticate(!null as UsernamePasswordAuthenticationToken)

        and: 'token returned'
        1 * tokensServiceMock.createToken(!null as String) >> getRandomWord()

        and: 'response ok'
        res.statusCode == OK

        and: 'response contains token'
        res.body.token
    }

    def 'get current user' () {
        given: 'authentication manager mock'
        def authenticationManagerMock = Mock(AuthenticationManager)

        and: 'tokens service'
        def tokensServiceMock = Mock(ITokensService)

        and: 'conversion service'
        def conversionServiceMock = Mock(ConversionService)

        and: 'auth context service'
        def authContextMock = Mock(AuthContext)

        def authController = new AuthController(
                authenticationManagerMock,
                tokensServiceMock,
                conversionServiceMock,
                authContextMock
        )

        and: 'valid user'
        def user = getUser()

        when: 'get token'
        def res = authController.getCurrentUser()

        then: 'user exists and authenticated'
        1 * authContextMock.getUser() >> user

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
}
