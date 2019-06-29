package com.app.bugtracker.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.bugtracker.exceptions.Exceptions;
import com.app.bugtracker.exceptions.NotFoundException;
import com.app.bugtracker.models.User;
import com.app.bugtracker.repositories.IUsersRepository;

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
        User user = usersRepository.findByEmail(username)
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
