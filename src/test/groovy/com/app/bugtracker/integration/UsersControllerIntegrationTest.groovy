package com.app.bugtracker.integration

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles('integration-test')
class UsersControllerIntegrationTest extends Specification  {
}
