package com.app.bugtracker.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.bugtracker.dto.user.CreateUserDTO;
import com.app.bugtracker.dto.user.UpdateUserDTO;
import com.app.bugtracker.dto.user.UserDTO;
import com.app.bugtracker.constants.Urls;
import com.app.bugtracker.services.user.IUsersService;

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
	private final ConversionService conversionService;
	
	@Autowired
	public UsersController(final IUsersService usersService, final ConversionService conversionService) {
		this.usersService = usersService;
		this.conversionService = conversionService;
	}

	@Override
	@GetMapping(path = { "/{id}" })
	@ApiOperation("Find user by id.")
	public ResponseEntity<UserDTO> findById(@PathVariable final UUID id) {
		return new ResponseEntity<>(conversionService.convert(usersService.findById(id),
			UserDTO.class), HttpStatus.OK);
	}

	@Override
	@GetMapping
	@ApiOperation("Find users.")
	public ResponseEntity<Page<UserDTO>> findAll(@RequestParam(value = "skip", required = false)final Integer skip,
			@RequestParam(value = "limit", required = false) final Integer limit) {
		return new ResponseEntity<>(usersService.findAll(skip, limit)
			.map(u -> conversionService.convert(u, UserDTO.class)), HttpStatus.OK);
	}

	// TODO: Fix custom validator
	@Override
	@PostMapping(consumes = "application/json", produces = "application/json")
	@ApiOperation("Create user.")
	public ResponseEntity<UserDTO> create(@RequestBody @Valid
	    final CreateUserDTO createUserDTO) {
		return new ResponseEntity<>(conversionService.convert(usersService.create(createUserDTO),
			UserDTO.class), HttpStatus.CREATED);
	}

	@Override
	@PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
	@ApiOperation("Update user.")
	public ResponseEntity<UserDTO> update(@PathVariable final UUID id,
	        @RequestBody @Valid final UpdateUserDTO updateUserDTO) {
		return new ResponseEntity<>(conversionService.convert(usersService.update(id, updateUserDTO),
			UserDTO.class), HttpStatus.OK);
	}

	@Override
	@DeleteMapping(path = { "/{id}" })
	@ApiOperation("Delete user by id.")
	public void deleteById(@PathVariable final UUID id) {
        usersService.deleteById(id);
    }
}
