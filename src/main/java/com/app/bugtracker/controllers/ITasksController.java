package com.app.bugtracker.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.app.bugtracker.constants.TaskStatus;
import com.app.bugtracker.constants.TaskType;
import com.app.bugtracker.dto.task.CreateTaskDTO;
import com.app.bugtracker.dto.task.TaskDTO;

public interface ITasksController {
    ResponseEntity<TaskDTO> findById(UUID id);
    ResponseEntity<Page<TaskDTO>> findAll(Integer skip, Integer limit);
    ResponseEntity<TaskDTO> create(CreateTaskDTO dto);
    ResponseEntity<TaskDTO> update(UUID id, CreateTaskDTO dto);
    void deleteById(UUID id);
    ResponseEntity<List<TaskStatus>> getTaskStatuses();
    ResponseEntity<List<TaskType>> getTaskTypes();
}
