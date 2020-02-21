package com.app.bugtracker.users.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

// TODO: Add custom validation.

/**
 * User create request.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {

    /**
     * Email.
     */
    @ApiModelProperty("User email.")
    @Email(message = "Email required.")
    private String email;

    /**
     * Username.
     */
    @ApiModelProperty("Username.")
    @NotEmpty(message = "Username required.")
    private String username;

    /**
     * Password.
     */
    @ApiModelProperty("User password.")
    @NotEmpty(message = "Password required.")
    private String password;

    /**
     * Confirm password.
     */
    @ApiModelProperty("Confirm password.")
    @NotEmpty(message = "Confirm password.")
    private String confirmPassword;

    /**
     * First name.
     */
    @ApiModelProperty("First name.")
    private String firstName;

    /**
     * Last name.
     */
    @ApiModelProperty("Last name.")
    private String lastName;
}
