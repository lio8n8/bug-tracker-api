package com.app.bugtracker.DTO.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Create user dto")
public class CreateUserDTO {
	private String email;
	@ApiModelProperty("Password")
	private String psw;
	@ApiModelProperty("Confirm password")
	private String confirmPsw; 
}
