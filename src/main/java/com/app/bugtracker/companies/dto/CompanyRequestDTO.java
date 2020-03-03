package com.app.bugtracker.companies.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Contains data for create/update company.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRequestDTO {

	/**
	* Id.
	*/
	@ApiModelProperty("id")
	private UUID id;

	/**
	* Name.
	*/
	@ApiModelProperty("name")
	private String name;

	/**
	* Title.
	*/
	@ApiModelProperty("title")
	private String title;

	/**
	* Description.
	*/
	@ApiModelProperty("description")
	private String description;
}
