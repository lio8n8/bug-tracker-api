package com.app.bugtracker.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.app.bugtracker.DTO.task.CreateTaskDTO;
import com.app.bugtracker.models.Task;

public interface ITasksController {
    ResponseEntity<Task> findById(UUID id);
    ResponseEntity<Page<Task>> findAll(Integer skip, Integer limit);
    ResponseEntity<Task> create(CreateTaskDTO dto);
    ResponseEntity<Task> update(UUID id, CreateTaskDTO dto);
    void deleteById(UUID id);
}
