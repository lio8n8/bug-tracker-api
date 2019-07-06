package com.app.bugtracker.services.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.bugtracker.DTO.user.CreateUserDTO;
import com.app.bugtracker.DTO.user.UpdateUserDTO;
import com.app.bugtracker.exceptions.Exceptions;
import com.app.bugtracker.exceptions.NotFoundException;
import com.app.bugtracker.models.User;
import com.app.bugtracker.repositories.IUsersRepository;

/**
 * Users service
 */
@Service
public class UsersService implements IUsersService {
    private final IUsersRepository usersRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersService(IUsersRepository usersRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Finds user by id
     */
    @Override
    public User findById(final UUID id) {
        return usersRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Exceptions.USER_NOT_FOUND));
    }
    
    /**
     * Find user by email.
     * @param email
     * @return {@link User}
     */
    @Override
    public User findByEmail(String email) {
        return usersRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException(Exceptions.USER_NOT_FOUND));
    }

    /**
     * Finds all users
     */
    @Override
    public Page<User> findAll(final Integer skip, final Integer limit) {
        return usersRepository.findAll(PageRequest.of(skip, limit));
    }

    /**
     * Create user
     * @param createUserDTO
     * @return {@link User}
     */
    @Override
    public User create(final CreateUserDTO createUserDTO) {
        User user = User.builder()
                .email(createUserDTO.getEmail())
                .psw(bCryptPasswordEncoder.encode(createUserDTO.getPsw()))
                .build();
        
        return usersRepository.save(user);
    }

    /**
     * Update user.
     * @param id user id
     * @param updateUserDTO
     * @return {@link User}
     */
    @Override
    public User update(final UUID id, final UpdateUserDTO updateUserDTO) {
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Exceptions.USER_NOT_FOUND));
        
        user.setEmail(updateUserDTO.getEmail());
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        
        return usersRepository.save(user);
    }

    /**
     * Delete user by id.
     * @param id
     */
    @Override
    public void deleteById(final UUID id) {
        usersRepository.deleteById(id);
    }

    @Override
    public void resetPassword(String currentPsw, String newPsw, String confirmNewPsw) {
        // TODO Auto-generated method stub

    }
}
