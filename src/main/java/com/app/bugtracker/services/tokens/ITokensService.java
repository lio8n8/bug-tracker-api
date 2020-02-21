package com.app.bugtracker.services.tokens;

import org.springframework.security.core.Authentication;
import javax.servlet.http.HttpServletRequest;

/**
 * Tokens service interface.
 */
public interface ITokensService {

    /**
     * Creates a new token.
     * @param username username.
     * @return token.
     */
    String createToken(String username);

    Authentication getAuthentication(HttpServletRequest req);
}
