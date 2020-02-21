package com.app.bugtracker.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * User login request data.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequest {

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
}
