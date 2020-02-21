package com.app.bugtracker.users.services;

import com.app.bugtracker.users.dto.UserCreateRequest;
import com.app.bugtracker.users.dto.UserUpdateRequest;
import com.app.bugtracker.users.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Users service interface.
 */
public interface IUsersService {

    /**
     * Finds user by id.
     * @param id user id.
     * @return {@link User}.
     */
    User findById(UUID id);

    /**
     * Finds user by email.
     * @param email user email.
     * @return {@link User}.
     */
    User findByEmail(String email);

    /**
     * Finds user by username.
     * @param username username.
     * @return {@link User}.
     */
    User findByUsername(String username);

    /**
     * Finds all users.
     * @param query query.
     * @return {@link Page} with list of {@link User};
     */
    Page<User> findAll(Pageable query);

    /**
     * Creates a new user.
     * @param request create user request.
     * @return created {@link User}.
     */
    User create(UserCreateRequest request);

    /**
     * Updates user.
     * @param id user id.
     * @param request update user request.
     * @return updated {@link User}.
     */
    User update(UUID id, UserUpdateRequest request);

    /**
     * Deletes user by id.
     * @param id user id.
     */
    void deleteById(UUID id);
}
