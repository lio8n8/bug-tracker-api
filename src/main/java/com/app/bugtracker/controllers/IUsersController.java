package com.app.bugtracker.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.app.bugtracker.Dto.CreateUserDto;
import com.app.bugtracker.Dto.UpdateUserDto;
import com.app.bugtracker.models.User;

public interface IUsersController {
	ResponseEntity<User> findById(UUID id);
	ResponseEntity<Page<User>> findAll(Integer skip, Integer limit);
	ResponseEntity<User> create(CreateUserDto dto);
	ResponseEntity update(UpdateUserDto dto);
	void deleteById(UUID id);
}
