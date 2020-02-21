package com.app.bugtracker


import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

@ActiveProfiles('test')
@SpringBootTest(classes = AuthApplication,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BaseAcceptanceTest extends Specification {

    protected WebTestClient webTestClient = WebTestClient
            .bindToServer()
            .baseUrl('http://127.0.0.1:8081')
            .build()
}
