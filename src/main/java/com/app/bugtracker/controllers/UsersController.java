package com.app.bugtracker.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.bugtracker.DTO.user.CreateUserDTO;
import com.app.bugtracker.DTO.user.UpdateUserDTO;
import com.app.bugtracker.constants.Urls;
import com.app.bugtracker.models.User;
import com.app.bugtracker.services.IUsersService;
import com.app.bugtracker.validators.CreateUserValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Users controller
 */
@RestController
@RequestMapping({ Urls.USERS })
@Api(tags = "users-controller", description = "Users Controller")
public class UsersController implements IUsersController {
	private final IUsersService usersService;
	
	@Autowired
	public UsersController(IUsersService usersService) {
		this.usersService = usersService;
	}

	@Override
	@GetMapping(path = { "/{id}" })
	@ApiOperation("Find user by id.")
	public ResponseEntity<User> findById(@PathVariable final UUID id) {
		return usersService.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	@Override
	@GetMapping
	@ApiOperation("Find users.")
	public ResponseEntity<Page<User>> findAll(@RequestParam(value = "skip", required = false)final Integer skip,
			@RequestParam(value = "limit", required = false) final Integer limit) {
		return new ResponseEntity<Page<User>>(usersService.findAll(skip, limit), HttpStatus.OK);
	}

	// TODO: Fix custom validator
	@Override
	@PostMapping(consumes = "application/json", produces = "application/json")
	@ApiOperation("Create user.")
	public ResponseEntity<User> create(@RequestBody @Valid
	    final CreateUserDTO createUserDTO) {
		return new ResponseEntity<>(usersService.create(createUserDTO), HttpStatus.CREATED);
	}

	@Override
	@PutMapping(value = "/{id}")
	@ApiOperation("Update user.")
	public ResponseEntity<User> update(@PathVariable final UUID id,
	        @RequestBody @Valid final UpdateUserDTO updateUserDTO) {
	    return new ResponseEntity<>(usersService.update(id, updateUserDTO), HttpStatus.OK);
	}

	@Override
	@DeleteMapping(path = { "/{id}" })
	@ApiOperation("Delete user by id.")
	public void deleteById(@PathVariable final UUID id) {
        usersService.deleteById(id);
    }

}
