package com.app.bugtracker.filters;

import com.app.bugtracker.auth.services.ITokensService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

/**
 * Jwt token authentication filter.
 */
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private ITokensService tokensService;

    @Autowired
    public JwtTokenAuthenticationFilter(final ITokensService tokensService) {
        this.tokensService = tokensService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            getContext()
                    .setAuthentication(
                            tokensService.getAuthentication(request)
                    );
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED, e.getMessage()
            );
        }
    }
}
