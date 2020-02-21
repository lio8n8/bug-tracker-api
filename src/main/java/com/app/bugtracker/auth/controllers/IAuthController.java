package com.app.bugtracker.auth.controllers;

import com.app.bugtracker.auth.dto.AuthResponseDTO;
import com.app.bugtracker.users.dto.UserDTO;
import com.app.bugtracker.auth.dto.UserLoginRequest;
import org.springframework.http.ResponseEntity;

/**
 * Auth controller interface.
 */
public interface IAuthController {

    /**
     * Creates and returns a new token.
     * @param request user login request.
     * @return token.
     */
    ResponseEntity<AuthResponseDTO> getToken(UserLoginRequest request);

    /**
     * Get authenticated user from context.
     *
     * @return {@link UserDTO}
     */
    ResponseEntity<UserDTO> getCurrentUser();
}
