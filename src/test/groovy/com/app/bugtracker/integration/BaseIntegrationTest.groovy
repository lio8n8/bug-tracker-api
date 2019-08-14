package com.app.bugtracker.integration

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.context.ActiveProfiles

import spock.lang.Specification

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles('integration-test')
class BaseIntegrationTest extends Specification {
    final protected BASE_URL = 'http://127.0.0.1:8080/api'
}
