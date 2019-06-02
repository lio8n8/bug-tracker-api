package com.app.bugtracker.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.app.bugtracker.Dto.CreateUserDto;
import com.app.bugtracker.Dto.UpdateUserDto;
import com.app.bugtracker.models.User;

public interface IUsersController {
	ResponseEntity<User> findById(String id);
	ResponseEntity<Page<User>> findAll(int skip, int limit);
	ResponseEntity<User> create(CreateUserDto dto);
	ResponseEntity update(UpdateUserDto dto);
	void deleteById(String id);
}
