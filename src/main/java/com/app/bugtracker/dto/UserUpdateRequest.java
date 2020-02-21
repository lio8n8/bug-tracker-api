package com.app.bugtracker.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

/**
 * User update request data.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {

    /**
     * Email.
     */
    @ApiModelProperty("User email.")
    @Email(message = "Email required.")
    private String email;

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
