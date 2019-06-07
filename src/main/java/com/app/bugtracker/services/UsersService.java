package com.app.bugtracker.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.bugtracker.DTO.user.CreateUserDTO;
import com.app.bugtracker.DTO.user.UpdateUserDTO;
import com.app.bugtracker.models.User;
import com.app.bugtracker.repositories.IUsersRepository;

/**
 * Users service
 */
@Service
public class UsersService implements IUsersService {
    private final IUsersRepository usersRepository;

    @Autowired
    public UsersService(IUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Finds user by id
     */
    @Override
    public Optional<User> findById(UUID id) {
        return usersRepository.findById(id);
    }

    /**
     * Finds all users
     */
    @Override
    public Page<User> findAll(Integer skip, Integer limit) {
        return usersRepository.findAll(PageRequest.of(skip, limit));
    }

    @Override
    public User create(CreateUserDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User update(UpdateUserDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteById(UUID id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void resetPassword(String currentPsw, String newPsw, String confirmNewPsw) {
        // TODO Auto-generated method stub

    }
}
