package com.app.bugtracker.controllers;

import org.springframework.http.ResponseEntity;

import com.app.bugtracker.dto.user.SigninUserDTO;
import com.app.bugtracker.dto.user.UserAuthResponseDTO;

public interface IAuthController {

    /**
     * Signin
     * @param signinUserDTO
     *
     * @return {@link UserAuthResponseDTO}
     */
    ResponseEntity<UserAuthResponseDTO> signin(SigninUserDTO signinUserDTO);
}
