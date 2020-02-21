package com.app.bugtracker.users.services;

import com.app.bugtracker.exceptions.Exceptions;
import com.app.bugtracker.exceptions.NotFoundException;
import com.app.bugtracker.users.models.User;
import com.app.bugtracker.users.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom user details service.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private IUsersRepository usersRepository;

    @Autowired
    public CustomUserDetailsService(IUsersRepository usersRepository) {
        super();
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(Exceptions.USER_NOT_FOUND));

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPsw())
                .authorities("USER")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
