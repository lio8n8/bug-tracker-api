package com.app.bugtracker.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.bugtracker.models.user.User;

@Repository
public interface IUsersRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
