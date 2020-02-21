package com.app.bugtracker.services.users;

import com.app.bugtracker.dto.UserCreateRequest;
import com.app.bugtracker.dto.UserUpdateRequest;
import com.app.bugtracker.exceptions.NotFoundException;
import com.app.bugtracker.models.User;
import com.app.bugtracker.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.app.bugtracker.exceptions.Exceptions.USER_NOT_FOUND;

/**
 * Users service implementation.
 */
@Service
public class UsersService implements IUsersService {

    /**
     * Users repository.
     */
    private final IUsersRepository usersRepository;

    /**
     * BCrypt password encoder.
     */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Users service constructor.
     * @param usersRepository users repository.
     */
    @Autowired
    public UsersService(
            final IUsersRepository usersRepository,
            final BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(final UUID id) {
            return usersRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByEmail(final String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByUsername(final String username) {
        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<User> findAll(final Pageable query) {
        return usersRepository.findAll(query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User create(final UserCreateRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .psw(bCryptPasswordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .locked(true)
                .build();

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        return usersRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User update(
            final UUID id, final UserUpdateRequest request) {
        User user = findById(id);

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUpdatedAt(LocalDateTime.now());

        return usersRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(final UUID id) {
        usersRepository.deleteById(id);
    }
}
