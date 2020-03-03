package com.app.bugtracker.companies.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Contains company data.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDTO {

	/**
	* Company id.
	*/
	private UUID id;

	/**
	* Company name.
	*/
	private String name;

	/**
	* Company title.
	*/
	private String title;

	/**
	* Company description.
	*/
	private String description;
}
