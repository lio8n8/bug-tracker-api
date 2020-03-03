package com.app.bugtracker.companies.converters;

import com.app.bugtracker.companies.dto.CompanyDTO;
import com.app.bugtracker.companies.models.Company;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts Companies to CompaniesDTO.
 */
@Component
public class CompaniesDTOCoverter implements Converter<Company, CompanyDTO> {

    @Override
    public CompanyDTO convert(Company source) {
        return CompanyDTO.builder()
                .id(source.getId())
                .name(source.getName())
                .title(source.getTitle())
                .description(source.getDescription())
                .build();
    }
}
