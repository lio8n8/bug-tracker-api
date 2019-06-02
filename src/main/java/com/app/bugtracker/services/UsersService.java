package com.app.bugtracker.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.app.bugtracker.models.User;
import com.app.bugtracker.repositories.IUsersRepository;

/**
 * Users service
 */
public class UsersService implements IUsersService{
	private final IUsersRepository usersRepository;
	
	@Autowired
	public UsersService(IUsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}
	
	/**
	 * Finds user by id
	 */
	@Override
	public Optional<User> findById(String id) {
		return usersRepository.findById(id);
	}

	/**
	 * Finds all users
	 */
	@Override
	public Page<User> findAll(int skip, int limit) {
		return usersRepository.findAll(PageRequest.of(skip, limit));
	}

	@Override
	public User create(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetPassword(String currentPsw, String newPsw, String confirmNewPsw) {
		// TODO Auto-generated method stub
		
	}	
}
