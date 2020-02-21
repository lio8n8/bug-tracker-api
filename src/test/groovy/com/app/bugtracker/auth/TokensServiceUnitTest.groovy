package com.app.bugtracker.auth

import com.app.bugtracker.auth.services.TokensService
import com.app.bugtracker.users.services.CustomUserDetailsService
import spock.lang.Specification

import static com.app.bugtracker.Utils.getApplicationConfigs
import static com.app.bugtracker.Utils.getUser

class TokensServiceUnitTest extends Specification {

    def 'create token'() {
        given: 'custom details service mock'
        def customUserDetailsServiceMock = Mock(CustomUserDetailsService)

        and: 'applications configs'
        def applicationConfigs = getApplicationConfigs()

        and: 'user'
        def user = getUser()

        and: 'tokens service'
        def tokensService = new TokensService(customUserDetailsServiceMock, applicationConfigs)

        when: 'create token'
        tokensService.createToken(user.username)

        then: 'token created'
        true
    }
}
