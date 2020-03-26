package com.app.bugtracker.companies.controllers;

import com.app.bugtracker.companies.dto.CompanyDTO;
import com.app.bugtracker.companies.dto.CompanyRequestDTO;
import com.app.bugtracker.companies.services.ICompaniesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import java.util.UUID;

import static com.app.bugtracker.Urls.COMPANY;
import static com.app.bugtracker.Urls.COMPANIES;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Implements {@link ICompaniesService}
 */
@RestController
@Api(tags = "companies-controller")
@Slf4j
public class CompaniesController implements ICompaniesController {

    /**
     * ICompaniesService.
     */
    private final ICompaniesService companiesService;

    /**
     * Conversion service.
     */
    private final ConversionService conversionService;

    public CompaniesController(final ICompaniesService companiesService,
                              final ConversionService conversionService) {
        this.companiesService = companiesService;
        this.conversionService = conversionService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = COMPANY)
    @ApiOperation(value = "Find company by id.", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyDTO> findById(@PathVariable("id") final UUID id) {

        return new ResponseEntity<>(conversionService.convert(
            companiesService.findById(id), CompanyDTO.class
        ), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = COMPANIES)
    @ApiOperation(value = "Find all companies.")
    public ResponseEntity<Page<CompanyDTO>> findAll(
            @PageableDefault(page = 0, size = 25)
            final Pageable request) {

        return new ResponseEntity<>(companiesService.findAll(request).map(
                c -> conversionService.convert(c, CompanyDTO.class)
        ), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping(path = COMPANIES,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("Create a new company.")
    public ResponseEntity<CompanyDTO> create(
            @Valid @RequestBody final CompanyRequestDTO request) {

        return new ResponseEntity<>(conversionService.convert(
            companiesService.create(request), CompanyDTO.class
        ), CREATED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PutMapping(path = COMPANY,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("Update company.")
    public ResponseEntity<CompanyDTO> update(
            @PathVariable("id") final UUID id,
            @Valid @RequestBody final CompanyRequestDTO request) {

        return new ResponseEntity<>(conversionService.convert(
            companiesService.update(id, request), CompanyDTO.class
        ), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DeleteMapping(path = COMPANY)
    @ApiOperation("Delete company by id.")
    public ResponseEntity deleteById(@PathVariable("id") final UUID id) {
        companiesService.deleteById(id);

        return new ResponseEntity(NO_CONTENT);
    }
}
