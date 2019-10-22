package com.app.bugtracker.services.user;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.app.bugtracker.dto.user.CreateUserDTO;
import com.app.bugtracker.dto.user.UpdateUserDTO;
import com.app.bugtracker.models.user.User;

/**
 * IUserService
 */
public interface IUsersService {
    /**
     * Find user by id.
     * @param id
     * @return {@link User}
     */
    User findById(UUID id);
    
    /**
     * Find user by email.
     * @param email
     * @return {@link User}
     */
    User findByEmail(String email);

    Page<User> findAll(Integer skip, Integer limit);

    /**
     * Create user.
     * @param createUserDTO
     * @return {@link User}
     */
    User create(CreateUserDTO createUserDTO);

    /**
     * Update user.
     * @param id user id
     * @param updateUserDTO
     * @return {@link User}
     */
    User update(UUID id, UpdateUserDTO updateUserDTO);

    /**
     * Delete user by id.
     * @param id
     */
    void deleteById(UUID id);

    void resetPassword(String currentPsw, String newPsw, String confirmNewPsw);
}
