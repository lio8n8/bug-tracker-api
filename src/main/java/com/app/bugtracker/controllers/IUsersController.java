package com.app.bugtracker.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.app.bugtracker.dto.user.CreateUserDTO;
import com.app.bugtracker.dto.user.UpdateUserDTO;
import com.app.bugtracker.dto.user.UserDTO;

public interface IUsersController {
    /**
     * Find user by id.
     * @param id user id
     *
     * @return {@link UserDTO}
     */
	ResponseEntity<UserDTO> findById(UUID id);
	
	/**
	 * Find all users.
	 * @param skip
	 * @param limit
	 *
	 * @return list of {@link UserDTO}
	 */
	ResponseEntity<Page<UserDTO>> findAll(Integer skip, Integer limit);
	
	/**
	 * Create user.
	 * @param createUserDTO user data
	 *
	 * @return {@link UserDTO}
	 */
	ResponseEntity<UserDTO> create(CreateUserDTO createUserDTO);

	/**
	 * Update user
	 * @param id user id.
	 * @param updateUserDTO
	 *
	 * @return {@link UserDTO}
	 */
    ResponseEntity<UserDTO> update(UUID id, UpdateUserDTO updateUserDTO);

    /**
     * Delete user by id.
     * @param id user id
     */
    void deleteById(UUID id);
}
