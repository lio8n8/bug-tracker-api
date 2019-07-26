package com.app.bugtracker.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.app.bugtracker.dto.user.CreateUserDTO;
import com.app.bugtracker.dto.user.UpdateUserDTO;
import com.app.bugtracker.dto.user.UserDTO;

public interface IUsersController {
	ResponseEntity<UserDTO> findById(UUID id);
	ResponseEntity<Page<UserDTO>> findAll(Integer skip, Integer limit);
	ResponseEntity<UserDTO> create(CreateUserDTO dto);
	ResponseEntity<UserDTO> update(UUID id, UpdateUserDTO dto);
	void deleteById(UUID id);
}
