package com.app.bugtracker.companies.repositories;

import com.app.bugtracker.companies.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Companies repository interface.
 */
public interface ICompaniesRepository extends JpaRepository<Company, UUID> {

}
