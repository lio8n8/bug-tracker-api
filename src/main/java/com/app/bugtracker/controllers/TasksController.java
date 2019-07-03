package com.app.bugtracker.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.bugtracker.DTO.task.CreateTaskDTO;
import com.app.bugtracker.constants.Urls;
import com.app.bugtracker.models.Task;
import com.app.bugtracker.services.task.ITasksService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Tasks controller
 */
@RestController
@RequestMapping({ Urls.TASKS })
@Api(tags = "tasks-controller", description = "Tasks Controller")
public class TasksController implements ITasksController {
    
    private final ITasksService tasksService;
    
    @Autowired
    public TasksController(final ITasksService tasksService) {
        this.tasksService = tasksService;
    }

    @Override
    @GetMapping(path = { "/{id}" })
    @ApiOperation("Find task by id.")
    public ResponseEntity<Task> findById(@PathVariable final UUID id) {
        return tasksService.findById(id).map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping
    @ApiOperation("Find tasks.")
    public ResponseEntity<Page<Task>> findAll(@RequestParam(value = "skip", required = false)final Integer skip,
            @RequestParam(value = "limit", required = false) final Integer limit) {
        return new ResponseEntity<Page<Task>>(tasksService.findAll(skip, limit), HttpStatus.OK);
    }

    @Override
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Create task.")
    public ResponseEntity<Task> create(@RequestBody @Valid final CreateTaskDTO createTaskDTO) {
        return new ResponseEntity<>(tasksService.create(createTaskDTO), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    @ApiOperation("Update task.")
    public ResponseEntity<Task> update(@PathVariable final UUID id,
            @RequestBody @Valid final CreateTaskDTO createTaskDTO) {
        return new ResponseEntity<>(tasksService.update(id, createTaskDTO), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(path = { "/{id}" })
    @ApiOperation("Delete task by id.")
    public void deleteById(@PathVariable final UUID id) {
        tasksService.deleteById(id);        
    }

}
