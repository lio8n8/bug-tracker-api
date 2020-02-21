package com.app.bugtracker.auth.services;

import com.app.bugtracker.configs.ApplicationConfigs;
import com.app.bugtracker.users.services.CustomUserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Tokens service.
 */
@Service
public class TokensService implements ITokensService {

    /**
     * Custom details service.
     */
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Application configs.
     */
    private ApplicationConfigs applicationConfigs;

    @Autowired
    public TokensService(CustomUserDetailsService customUserDetailsService,
                         ApplicationConfigs applicationConfigs) {
        this.customUserDetailsService = customUserDetailsService;
        this.applicationConfigs = applicationConfigs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Authentication getAuthentication(final HttpServletRequest req) {
        String token = req.getHeader("Authorization");

        if (null != token && token.length() > 0) {
            String username = Jwts.parser()
                    .setSigningKey(applicationConfigs.getTokenConfigs().getSecretKey())
                    .parseClaimsJws(token.replace("Bearer", ""))
                    .getBody()
                    .getSubject();

            if (null == username || username.length() <= 0) {
                return null;
            }

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createToken(final String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() +
                applicationConfigs.getTokenConfigs().getExpiredIn());

        return Jwts.builder()
                .setClaims(Jwts.claims()
                        .setSubject(username))
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512,
                        applicationConfigs.getTokenConfigs().getSecretKey())
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(applicationConfigs.getTokenConfigs().getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
