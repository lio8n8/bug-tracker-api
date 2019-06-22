package com.app.bugtracker.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.app.bugtracker.DTO.user.CreateUserDTO;
import com.app.bugtracker.DTO.user.UpdateUserDTO;
import com.app.bugtracker.models.User;

public interface IUsersController {
	ResponseEntity<User> findById(UUID id);
	ResponseEntity<Page<User>> findAll(Integer skip, Integer limit);
	ResponseEntity<User> create(CreateUserDTO dto);
	ResponseEntity<User> update(UUID id, UpdateUserDTO dto);
	void deleteById(UUID id);
}
