package com.app.bugtracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bugtracker.DTO.user.SigninUserDTO;
import com.app.bugtracker.DTO.user.UserAuthResponseDTO;
import com.app.bugtracker.constants.Urls;
import com.app.bugtracker.services.auth.IJwtTokenService;
import com.app.bugtracker.services.user.IUsersService;

import io.swagger.annotations.ApiOperation;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import javax.validation.Valid;

/**
 * Auth controller
 */
@RestController
@RequestMapping({ Urls.Auth })
public class AuthController implements IAuthController {
    
    private IUsersService usersService;
    private IJwtTokenService jwtTokenService;
    private AuthenticationManager authenticationManager;
    
    @Autowired
    public AuthController(IUsersService usersService, IJwtTokenService jwtTokenService,
            AuthenticationManager authenticationManager) {
        this.usersService = usersService;
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Signin")
    public ResponseEntity<UserAuthResponseDTO> signin(@RequestBody @Valid final SigninUserDTO signinUserDTO) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signinUserDTO.getEmail(), signinUserDTO.getPsw()));
        
        getContext().setAuthentication(auth);
        String token = jwtTokenService.createToken(signinUserDTO.getEmail());

        // TODO: Create converter
        return new ResponseEntity<>(UserAuthResponseDTO.builder()
                .token(token).build(), HttpStatus.OK);
    }
}
