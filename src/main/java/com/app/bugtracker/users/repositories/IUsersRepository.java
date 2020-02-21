package com.app.bugtracker.users.repositories;

import com.app.bugtracker.users.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUsersRepository extends JpaRepository<User, UUID> {

    /**
     * Finds user by email.
     * @param email user email.
     * @return {@link User}.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds user by username.
     * @param username username.
     * @return {@link User}.
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds all users.
     * @param pageable request.
     * @return {@link Page} with list of {@link User}.
     */
    Page<User> findAll(Pageable pageable);
}
