package com.app.bugtracker.companies.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

/**
 * Companies entity.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "companies")
public class Company {

    /**
     * Companies id.
     */
    @Id
    @GeneratedValue(strategy = AUTO)
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
