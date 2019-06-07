package com.app.bugtracker.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersService(IUsersRepository usersRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    /**
     * Create user
     * @param createUserDTO
     * @return {@link User}
     */
    @Override
    public User create(CreateUserDTO createUserDTO) {
        User user = User
                .builder()
                .email(createUserDTO.getEmail())
                .psw(bCryptPasswordEncoder.encode(createUserDTO.getPsw()))
                .build();
        
        return usersRepository.save(user);
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
