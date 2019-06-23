package com.app.bugtracker.services.auth;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenService implements IJwtTokenService {
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    JwtTokenService(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    // TODO: Move to configs
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey = "secretKey";

    // TODO: Move to configs
    @Value("${security.jwt.token.expire-length:3600000}")
    private long expiredIn = 3600000; // 1h

    @Override
    public String createToken(final String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiredIn);

        return Jwts.builder()
            .setClaims(Jwts.claims().setSubject(username))
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }
    
    @Override
    public Authentication getAuthentication(final HttpServletRequest req) {
        return null;
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
