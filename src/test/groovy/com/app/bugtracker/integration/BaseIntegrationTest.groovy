package com.app.bugtracker.integration

import org.springframework.test.context.ActiveProfiles
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

import spock.lang.Specification

import com.app.bugtracker.BugTrackerApplication

@ActiveProfiles('test')
@SpringBootTest(classes = BugTrackerApplication,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BaseIntegrationTest extends Specification {

    protected WebTestClient webTestClient = WebTestClient
            .bindToServer()
            .baseUrl('http://127.0.0.1:8081')
            .build()
}
