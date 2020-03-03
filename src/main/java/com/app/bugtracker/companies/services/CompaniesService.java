package com.app.bugtracker.companies.services;

import com.app.bugtracker.exceptions.NotFoundException;
import com.app.bugtracker.companies.dto.CompanyRequestDTO;
import com.app.bugtracker.companies.models.Company;
import com.app.bugtracker.companies.repositories.ICompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.app.bugtracker.exceptions.Exceptions.COMPANY_NOT_FOUND;

/**
 * Companies service implementation.
 */
@Service
public class CompaniesService implements ICompaniesService {

    /**
     * Companies repository.
     */
    private final ICompaniesRepository companiesRepository;

    @Autowired
    public CompaniesService(final ICompaniesRepository companiesRepository) {
        this.companiesRepository = companiesRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Company findById(final UUID id) {
        return companiesRepository.findById(id).orElseThrow(
                () -> new NotFoundException(COMPANY_NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Company> findAll(final Pageable request) {
        return companiesRepository.findAll(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Company create(final CompanyRequestDTO request) {

        return companiesRepository.save(Company.builder()
                .name(request.getName())
                .title(request.getTitle())
                .description(request.getDescription())
                .build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Company update(final UUID id, final CompanyRequestDTO request) {
        Company companies = findById(id);

        companies.setName(request.getName());
        companies.setTitle(request.getTitle());
        companies.setDescription(request.getDescription());

        return companiesRepository.save(companies);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(final UUID id) {
        companiesRepository.deleteById(id);
    }
}
