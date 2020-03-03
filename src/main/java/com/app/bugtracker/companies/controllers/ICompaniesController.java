package com.app.bugtracker.companies.controllers;

import com.app.bugtracker.companies.dto.CompanyDTO;
import com.app.bugtracker.companies.dto.CompanyRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * Companies controller interface.
 */
public interface ICompaniesController {

    /**
     * Finds company by id.
     *
     * @param id company id
     * @return {@link CompanyDTO}
     */
    ResponseEntity<CompanyDTO> findById(UUID id);

    /**
     * Finds all companies.
     *
     * @param request pageable request
     * @return {@link Page} with list of {@link CompanyDTO}
     */
    ResponseEntity<Page<CompanyDTO>> findAll(Pageable request);

    /**
     * Create a new company.
     *
     * @param request create company request
     * @return created {@link CompanyDTO}
     */
    ResponseEntity<CompanyDTO> create(CompanyRequestDTO request);

    /**
     * Update company.
     *
     * @param id company id.
     * @param request update company request
     * @return updated {@link CompanyDTO}
     */
    ResponseEntity<CompanyDTO> update(UUID id, CompanyRequestDTO request);

    /**
     * Deletes company by id.
     *
     * @param id company id
     * @return empty response
     */
    ResponseEntity deleteById(UUID id);
}
