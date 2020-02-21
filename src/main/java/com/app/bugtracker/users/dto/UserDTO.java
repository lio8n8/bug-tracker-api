package com.app.bugtracker.users.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Contains user data.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    /**
     * User id.
     */
    private UUID id;

    /**
     * Email.
     */
    private String email;

    /**
     * Username.
     */
    private String username;

    /**
     * First name.
     */
    private String firstName;

    /**
     * Last name.
     */
    private String lastName;

    /**
     * Account locked.
     */
    private Boolean locked;

    /**
     * Account creation date.
     */
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * Account activation date.
     */
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime activatedAt;

    /**
     * Account updating date.
     */
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime updatedAt;

    /**
     * User's last login.
     */
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime lastLogin;
}
