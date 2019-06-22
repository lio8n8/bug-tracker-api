package com.app.bugtracker.DTO.user;

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
@ApiModel(description = "Create user dto")
public class CreateUserDTO {    
    @NotEmpty(message = "Email is required.")
    @Email
    @ApiModelProperty("Email")
    private String email;
    
    @NotEmpty(message = "Password is required.")
    @ApiModelProperty("Password")
    private String psw;
    
    @NotEmpty(message = "Confirm password.")
    @ApiModelProperty("Confirm password")
    private String confirmPsw;
}
