
package com.app.bugtracker.companies.services;

import com.app.bugtracker.companies.dto.CompanyRequestDTO;
import com.app.bugtracker.companies.models.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Company service interface.
 */
public interface ICompaniesService {

    /**
     * Finds company by id.
     *
     * @param id company id
     * @return {@link Company}
     */
    Company findById(UUID id);

    /**
     * Finds all company.
     *
     * @param request pageable request
     * @return page with list of {@link Company}
     */
    Page<Company> findAll(Pageable request);

    /**
     * Creates a new company.
     *
     * @param request create company request
     * @return created {@link Company}
     */
    Company create(CompanyRequestDTO request);

    /**
     * Updates company.
     *
     * @param id company id
     * @param request update company request
     * @return updated {@link Company}
     */
    Company update(UUID id, CompanyRequestDTO request);

    /**
     * Deletes company by id.
     *
     * @param id company id
     */
    void deleteById(UUID id);
}
