package com.app.bugtracker

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles('test')
@SpringBootTest(classes = Application,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BaseServiceIntegrationTest extends Specification {
}
