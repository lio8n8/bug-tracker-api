package com.app.bugtracker.services.auth;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

public interface IJwtTokenService {
    String createToken(String username);
    Authentication getAuthentication(HttpServletRequest req);
}
