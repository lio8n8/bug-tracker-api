package com.app.bugtracker.tokens

import com.app.bugtracker.BaseIntegrationTest
import com.app.bugtracker.dto.UserCreateRequest
import com.app.bugtracker.services.users.IUsersService
import com.app.bugtracker.services.tokens.ITokensService
import org.springframework.beans.factory.annotation.Autowired

import static com.app.bugtracker.Utils.faker

class TokensServiceIntegrationTest extends BaseIntegrationTest {

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
