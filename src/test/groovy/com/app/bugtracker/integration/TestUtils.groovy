package com.app.bugtracker.integration

import org.springframework.http.HttpHeaders
import com.app.bugtracker.services.auth.IJwtTokenService

class TestUtils {
    public static HttpHeaders getAuthHttpHeaders(String email) {
        HttpHeaders headers = new HttpHeaders()
        headers.add(HttpHeaders.AUTHORIZATION, IJwtTokenService.createToken(email))

        return headers;
    }
}
