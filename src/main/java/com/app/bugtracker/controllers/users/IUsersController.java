package com.app.bugtracker.controllers.users;

import com.app.bugtracker.dto.UserCreateRequest;
import com.app.bugtracker.dto.UserDTO;
import com.app.bugtracker.dto.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * Users controller interface.
 */
public interface IUsersController {

    /**
     * Finds all users.
     * @param request request.
     * @return {@link Page} with list of {@link UserDTO}.
     */
    ResponseEntity<Page<UserDTO>> findAll(Pageable request);

    /**
     * Finds user by id.
     * @param id user id.
     * @return {@link UserDTO}.
     */
    ResponseEntity<UserDTO> findById(UUID id);

    /**
     * Create a new user.
     * @param request create user request.
     * @return created {@link UserDTO}.
     */
    ResponseEntity<UserDTO> create(UserCreateRequest request);

    /**
     * Updates user.
     * @param id user id.
     * @param request update user request.
     * @return updated {@link UserDTO}.
     */
    ResponseEntity<UserDTO> update(UUID id, UserUpdateRequest request);

    /**
     * Delete user by id.
     * @param id user id.
     * @return empty response.
     */
    ResponseEntity delete(UUID id);
}
