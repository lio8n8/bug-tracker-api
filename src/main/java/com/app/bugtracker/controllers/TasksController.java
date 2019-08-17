package com.app.bugtracker.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
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

import com.app.bugtracker.dto.task.CreateTaskDTO;
import com.app.bugtracker.dto.task.TaskDTO;
import com.app.bugtracker.constants.TaskPriority;
import com.app.bugtracker.constants.TaskStatus;
import com.app.bugtracker.constants.TaskType;
import com.app.bugtracker.constants.Urls;
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
    private final ConversionService conversionService;
    
    @Autowired
    public TasksController(final ITasksService tasksService, final ConversionService conversionService) {
        this.tasksService = tasksService;
        this.conversionService = conversionService;
    }

    /**
     * Find task by id.
     * @param id task id
     *
     * @return {@link TaskDTO}
     */
    @Override
    @GetMapping(path = { "/{id}" })
    @ApiOperation("Find task by id.")
    public ResponseEntity<TaskDTO> findById(@PathVariable final UUID id) {
        return new ResponseEntity<>(conversionService.convert(tasksService.findById(id), TaskDTO.class),
            HttpStatus.OK);
    }

    /**
     * Find all tasks.
     * @param skip
     * @param limit
     *
     * @return {@link Page}
     */
    @Override
    @GetMapping
    @ApiOperation("Find tasks.")
    public ResponseEntity<Page<TaskDTO>> findAll(@RequestParam(value = "skip", required = false)final Integer skip,
            @RequestParam(value = "limit", required = false) final Integer limit) {
        return new ResponseEntity<>(tasksService.findAll(skip, limit)
            .map(t -> conversionService.convert(t, TaskDTO.class)), HttpStatus.OK);
    }

    /**
     * Create task.
     * @param createTaskDTO task data
     *
     * @return {@link TaskDTO}
     */
    @Override
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Create task.")
    public ResponseEntity<TaskDTO> create(@RequestBody @Valid final CreateTaskDTO createTaskDTO) {
        return new ResponseEntity<>(conversionService.convert(tasksService.create(createTaskDTO), TaskDTO.class),
            HttpStatus.CREATED);
    }

    /**
     * Update task.
     * @param id task id
     * @param createTaskDTO task data
     *
     * @return updated {@link TaskDTO}
     */
    @Override
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    @ApiOperation("Update task.")
    public ResponseEntity<TaskDTO> update(@PathVariable final UUID id,
            @RequestBody @Valid final CreateTaskDTO createTaskDTO) {
        return new ResponseEntity<>(conversionService.convert(tasksService.update(id, createTaskDTO), TaskDTO.class),
            HttpStatus.OK);
    }

    /**
     * Delete task by id.
     * @param id task id
     */
    @Override
    @DeleteMapping(path = { "/{id}" })
    @ApiOperation("Delete task by id.")
    public void deleteById(@PathVariable final UUID id) {
        tasksService.deleteById(id);        
    }
    
    /**
     * Get all possible task statuses.
     *
     * @return {@link TaskStatus}
     */
    @Override()
    @GetMapping(path = { "/statuses" })
    @ApiOperation("Get task statuses.")
    public ResponseEntity<List<TaskStatus>> getTaskStatuses() {
        return new ResponseEntity<List<TaskStatus>>(Arrays.asList(TaskStatus.values()), HttpStatus.OK);
    }
    
    /**
     * Get all possible task types.
     *
     * @return {@link TaskType}
     */
    @Override()
    @GetMapping(path = { "/types" })
    @ApiOperation("Get task types.")
    public ResponseEntity<List<TaskType>> getTaskTypes() {
        return new ResponseEntity<>(Arrays.asList(TaskType.values()), HttpStatus.OK);
    }
    
    /**
     * Get all possible task priorities.
     *
     * @return {@link TaskPriority}
     */
    @Override()
    @GetMapping(path = { "/priorities" })
    @ApiOperation("Get task types.")
    public ResponseEntity<List<TaskPriority>> getTaskPriorities() {
        return new ResponseEntity<>(Arrays.asList(TaskPriority.values()), HttpStatus.OK);
    }
}
