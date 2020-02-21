package com.app.bugtracker

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles('test')
@SpringBootTest(classes = AuthApplication,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BaseIntegrationTest extends Specification {
}
