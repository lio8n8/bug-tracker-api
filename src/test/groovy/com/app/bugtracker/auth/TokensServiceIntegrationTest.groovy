package com.app.bugtracker.auth

import com.app.bugtracker.BaseServiceIntegrationTest
import com.app.bugtracker.users.dto.UserCreateRequest
import com.app.bugtracker.users.services.IUsersService
import com.app.bugtracker.auth.services.ITokensService
import org.springframework.beans.factory.annotation.Autowired

import static com.app.bugtracker.Utils.faker

class TokensServiceIntegrationTest extends BaseServiceIntegrationTest {

    @Autowired
    private IUsersService usersService

    @Autowired
    private ITokensService tokensService

    def 'create token'() {
        given: 'user'
        def user = usersService.create(UserCreateRequest.builder()
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build())

        when: 'create token'
        def res = tokensService.createToken(user.username)

        then: 'token created'
        true
    }
}
