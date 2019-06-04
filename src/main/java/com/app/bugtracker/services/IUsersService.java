package com.app.bugtracker.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.app.bugtracker.models.User;

/**
 * IUserService
 */
public interface IUsersService {
	Optional<User> findById(UUID id);
	
	Page<User> findAll(Integer skip, Integer limit);
	
	User create(User user);
	
	User update(User user);
	
	void deleteById(UUID id);
	
	void resetPassword(String currentPsw, String newPsw, String confirmNewPsw);
}
