package com.app.bugtracker.tasks.converters;

import com.app.bugtracker.tasks.dto.TaskDTO;
import com.app.bugtracker.tasks.models.Task;
import com.app.bugtracker.users.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts Task to TaskDTO.
 */
@Component
public class TaskToTaskDTOConverter implements Converter<Task, TaskDTO> {

    /**
     * Conversion service.
     */
    private ConversionService conversionService;

    @Autowired
    public TaskToTaskDTOConverter(@Lazy ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public TaskDTO convert(Task source) {
        return TaskDTO.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .type(source.getType())
                .priority(source.getPriority())
                .status(source.getStatus())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())
                .createdBy(conversionService.convert(source.getCreatedBy(), UserDTO.class))
                .updatedBy(conversionService.convert(source.getUpdatedBy(), UserDTO.class))
                .build();
    }
}
