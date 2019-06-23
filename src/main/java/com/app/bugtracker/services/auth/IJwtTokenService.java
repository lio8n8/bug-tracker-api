package com.app.bugtracker.services.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

public interface IJwtTokenService {
    String createToken(String username);
    Authentication getAuthentication(HttpServletRequest req);
}
