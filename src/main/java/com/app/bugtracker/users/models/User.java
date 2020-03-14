package com.app.bugtracker.users.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

/**
 * User entity.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    /**
     * User id.
     */
    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID id;

    /**
     * Email.
     */
    @NotEmpty
    private String email;

    /**
     * Username.
     */
    @NotEmpty
    private String username;

    /**
     * Password.
     */
    @NotEmpty
    private String psw;

    /**
     * First name.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Last name.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Account locked (Default set true.)
     */
    private Boolean locked;

    /**
     * Account activation time.
     */
    @Column(name = "activated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant activatedAt;

    /**
     * Account creation time.
     */
    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant createdAt;

    /**
     * Time of account updating.
     */
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant updatedAt;

    /**
     * Time of last login.
     */
    @Column(name = "last_login", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant lastLogin;
}
