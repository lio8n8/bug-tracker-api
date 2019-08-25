package com.app.bugtracker.services.auth;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public interface IJwtTokenService {
    // TODO: Move to configs
    @Value("${security.jwt.token.secret-key:secret-key}")
    static String SECRET_KEY = "secretKey";

    // TODO: Move to configs
    @Value("${security.jwt.token.expire-length:3600000}")
    static long EXPIRED_IN = 3600000; // 1h

    static String createToken(final String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + EXPIRED_IN);

        return Jwts.builder()
                .setClaims(Jwts.claims()
                .setSubject(username))
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    Authentication getAuthentication(HttpServletRequest req);
}
