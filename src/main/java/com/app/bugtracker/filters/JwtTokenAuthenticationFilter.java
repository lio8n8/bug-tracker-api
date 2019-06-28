package com.app.bugtracker.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.bugtracker.services.auth.IJwtTokenService;

import io.jsonwebtoken.JwtException;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private IJwtTokenService jwtTokenService;
    
    @Autowired
    public JwtTokenAuthenticationFilter(IJwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            getContext()
                    .setAuthentication(
                            jwtTokenService.getAuthentication(
                                    (HttpServletRequest) request
                            )
                    );
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            ((HttpServletResponse) response).sendError(
                    HttpServletResponse.SC_UNAUTHORIZED, e.getMessage()
            );
        }        
    }

}
