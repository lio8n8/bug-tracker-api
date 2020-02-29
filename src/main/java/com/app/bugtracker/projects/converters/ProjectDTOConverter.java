package com.app.bugtracker.projects.converters;

import com.app.bugtracker.projects.dto.ProjectDTO;
import com.app.bugtracker.projects.models.Project;
import com.app.bugtracker.users.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts Project to ProjectDTO.
 */
@Component
public class ProjectDTOConverter implements Converter<Project, ProjectDTO> {

    /**
     * Conversion service.
     */
    private ConversionService conversionService;

    @Autowired
    public ProjectDTOConverter(@Lazy ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public ProjectDTO convert(Project source) {
        return ProjectDTO.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())
                .createdBy(conversionService.convert(source.getCreatedBy(), UserDTO.class))
                .updatedBy(conversionService.convert(source.getUpdatedBy(), UserDTO.class))
                .build();
    }
}
