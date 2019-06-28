package com.app.bugtracker.services.auth;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.app.bugtracker.exceptions.Exceptions;
import com.app.bugtracker.exceptions.NotFoundException;
import com.app.bugtracker.models.User;
import com.google.common.collect.Lists;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
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
        String token = req.getHeader(AUTHORIZATION);
        if (null != token && token.length() > 0) {
            String username = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token.replace("Bearer", ""))
                    .getBody()
                    .getSubject();

            if (null != username && username.length() > 0) {
                return null;
            }

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            return new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
        }
        
        return null;
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
