package com.app.bugtracker.controllers.auth;

import com.app.bugtracker.dto.AuthResponseDTO;
import com.app.bugtracker.dto.UserDTO;
import com.app.bugtracker.dto.UserLoginRequest;
import com.app.bugtracker.services.tokens.ITokensService;
import com.app.bugtracker.services.users.AuthContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.app.bugtracker.Urls.TOKENS;
import static com.app.bugtracker.Urls.USER_CURRENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Auth controller service.
 */
@RestController
@Api(tags = "auth-controller")
public class AuthController implements IAuthController{

    /**
     * Authentication manager.
     */
    private AuthenticationManager authenticationManager;

    /**
     * Tokens service.
     */
    private ITokensService tokensService;

    /**
     * Conversion service.
     */
    private final ConversionService conversionService;

    /**
     * Auth context.
     */
    private final AuthContext authContext;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          ITokensService tokensService,
                          ConversionService conversionService,
                          AuthContext authContext) {
        this.authenticationManager = authenticationManager;
        this.tokensService = tokensService;
        this.conversionService = conversionService;
        this.authContext = authContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping(path = TOKENS,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @ApiOperation("Get token.")
    public ResponseEntity<AuthResponseDTO> getToken(
            @Valid @RequestBody final UserLoginRequest request) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));
        getContext().setAuthentication(auth);

        return new ResponseEntity<>(AuthResponseDTO.builder()
                .token(
                    tokensService.createToken(request.getUsername())
                ).build(), HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = USER_CURRENT,
            produces = APPLICATION_JSON_VALUE)
    @ApiOperation("Find current user.")
    public ResponseEntity<UserDTO> getCurrentUser() {

        return new ResponseEntity<>(conversionService.convert(
                authContext.getUser(),
                UserDTO.class), OK);
    }
}
