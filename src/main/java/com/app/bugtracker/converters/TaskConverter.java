package com.app.bugtracker.converters;

import org.springframework.core.convert.converter.Converter;

import com.app.bugtracker.dto.task.TaskDTO;
import com.app.bugtracker.models.Task;

public class TaskConverter implements Converter<Task, TaskDTO> {

    @Override
    public TaskDTO convert(Task source) {
        return TaskDTO.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .priority(source.getPriority())
                .build();
    }

}
