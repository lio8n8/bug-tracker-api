package com.app.bugtracker.services.users;

import com.app.bugtracker.models.User;
import org.springframework.security.core.Authentication;

public interface IAuthContext {

    /**
     * Get authentication from context.
     * @return {@link Authentication}
     */
    Authentication getAuthentication();

    /**
     * Get user from context.
     * @return {@link User}
     */
    User getUser();
}
