package com.app.bugtracker.services;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.app.bugtracker.models.User;

/**
 * IUserService
 */
public interface IUsersService {
	Optional<User> findById(String id);
	
	Page<User> findAll(int skip, int limit);
	
	User create(User user);
	
	User update(User user);
	
	void deleteById(String id);
	
	void resetPassword(String currentPsw, String newPsw, String confirmNewPsw);
}
