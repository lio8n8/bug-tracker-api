package com.app.bugtracker.auth.services;

import com.app.bugtracker.exceptions.AuthenticationException;
import com.app.bugtracker.users.services.IUsersService;
import com.app.bugtracker.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.app.bugtracker.exceptions.Exceptions.ERROR_GET_USERNAME_FROM_CONTEXT;

@Service
public class AuthContext implements IAuthContext {

    @Autowired
    private IUsersService usersService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
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
