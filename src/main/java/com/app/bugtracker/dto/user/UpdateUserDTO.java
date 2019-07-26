package com.app.bugtracker.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Update user dto")
public class UpdateUserDTO {
    @NotEmpty(message = "Email is required.")
    @Email
    @ApiModelProperty("Email")
    private String email;
    
    @ApiModelProperty("First name")
    private String firstName;
    
    @ApiModelProperty("Last name")
    private String lastName;
}
