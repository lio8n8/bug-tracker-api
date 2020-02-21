package com.app.bugtracker.services.users;

import com.app.bugtracker.exceptions.AuthenticationException;
import com.app.bugtracker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.app.bugtracker.exceptions.Exceptions.ERROR_GET_USERNAME_FROM_CONTEXT;

@Service
public class AuthContext implements IAuthContext {

    @Autowired
    private IUsersService usersService;
    /**
     * {@inheritDoc}
     */
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser() {
        String username = getAuthentication().getName();

        if (null == username) {
            throw new AuthenticationException(ERROR_GET_USERNAME_FROM_CONTEXT);
        }

        return usersService.findByUsername(username);
    }
}
