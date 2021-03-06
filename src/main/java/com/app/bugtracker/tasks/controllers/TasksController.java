package com.app.bugtracker.tasks.controllers;

import com.app.bugtracker.tasks.dto.TaskDTO;
import com.app.bugtracker.tasks.dto.TaskRequest;
import com.app.bugtracker.tasks.dto.UserTaskRequestDTO;
import com.app.bugtracker.tasks.models.Priority;
import com.app.bugtracker.tasks.models.Status;
import com.app.bugtracker.tasks.models.Type;
import com.app.bugtracker.tasks.services.ITasksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.app.bugtracker.Urls.TASK;
import static com.app.bugtracker.Urls.TASKS;
import static com.app.bugtracker.Urls.TASK_ASSIGNEE;
import static com.app.bugtracker.Urls.TASK_ASSIGNEES;
import static com.app.bugtracker.Urls.TASK_STATUSES;
import static com.app.bugtracker.Urls.TASK_TYPES;
import static com.app.bugtracker.Urls.TASK_PRIORITIES;
import static com.app.bugtracker.Urls.TASKS_BY_ASSIGNEE;
import static com.app.bugtracker.Urls.TASKS_FOR_CURRENT_USER;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Implements {@link ITasksController}.
 */
@RestController
@Api(tags = "tasks-controller")
@Slf4j
public class TasksController implements ITasksController{

    /**
     * Tasks service.
     */
    private final ITasksService tasksService;

    /**
     * Conversion service.
     */
    private final ConversionService conversionService;

    public TasksController(ITasksService tasksService,
                           ConversionService conversionService) {
        this.tasksService = tasksService;
        this.conversionService = conversionService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = TASKS)
    @ApiOperation(value = "Find all tasks.")
    public ResponseEntity<Page<TaskDTO>> findAll(
            @PageableDefault(page = 0, size = 25)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "updatedAt", direction = Sort.Direction.ASC)
            })
            final Pageable request) {

        return new ResponseEntity<>(tasksService.findAll(request).map(
                t -> conversionService.convert(t, TaskDTO.class)
        ), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = TASK)
    @ApiOperation(value = "Find task by id.", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDTO> findById(@PathVariable("id") final UUID id) {

        return new ResponseEntity<>(conversionService.convert(
                tasksService.findById(id),
                TaskDTO.class
        ), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = TASK_TYPES, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find task types.")
    public ResponseEntity<List<Type>> findTaskTypes() {

        return new ResponseEntity<>(Arrays.asList(Type.values()), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = TASK_PRIORITIES, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find task priorities.")
    public ResponseEntity<List<Priority>> findTaskPriorities() {

        return new ResponseEntity<>(Arrays.asList(Priority.values()), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = TASK_STATUSES, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find task statuses")
    public ResponseEntity<List<Status>> findTaskStatuses() {

        return new ResponseEntity<>(Arrays.asList(Status.values()), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = TASKS_BY_ASSIGNEE)
    @ApiOperation(value = "Finds tasks by assignee id.", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<TaskDTO>> findByAssigneeId(
            @PathVariable("id") final UUID id,
            @PageableDefault(page = 0, size = 25)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "updatedAt", direction = Sort.Direction.ASC)
            }) final Pageable request) {

        return new ResponseEntity<>(tasksService.findByAssigneeId(id, request).map(
                t -> conversionService.convert(t, TaskDTO.class)
        ), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = TASKS_FOR_CURRENT_USER)
    @ApiOperation(value = "Finds tasks assigned to current user.", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<TaskDTO>> findByCurrentAssignee(
            @PageableDefault(page = 0, size = 25)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "updatedAt", direction = Sort.Direction.ASC)
            }) final Pageable request
    ) {

        return new ResponseEntity<>(tasksService.findByCurrentUser(request).map(
                t -> conversionService.convert(t, TaskDTO.class)
        ), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping(path = TASKS,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("Create task.")
    public ResponseEntity<TaskDTO> create(
            @RequestBody @Valid final TaskRequest request) {

        return new ResponseEntity<>(conversionService.convert(
                tasksService.create(request),
                TaskDTO.class
        ), CREATED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PutMapping(path = TASK,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("Update task.")
    public ResponseEntity<TaskDTO> update(
            @PathVariable("id") final UUID id,
            @RequestBody @Valid final TaskRequest request) {

        return new ResponseEntity<>(conversionService.convert(
                tasksService.update(id, request),
                TaskDTO.class
        ), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PatchMapping(path = TASK,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("Patch task.")
    public ResponseEntity<TaskDTO> patch(
            @PathVariable("id") final UUID id,
            @RequestBody @Valid final TaskRequest request) {
        throw new RuntimeException("Not yet implemented!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DeleteMapping(path = TASK)
    @ApiOperation("Delete task by id.")
    public ResponseEntity deleteById(@PathVariable("id") final UUID id) {
        tasksService.deleteById(id);

        return new ResponseEntity(NO_CONTENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping(path = TASK_ASSIGNEES,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("Add assignee to task.")
    public ResponseEntity<TaskDTO> assignTaskToUser(
            @PathVariable("taskId") final UUID taskId,
            @RequestBody @Valid final UserTaskRequestDTO request) {

        return new ResponseEntity<>(conversionService.convert(
                tasksService.assignTaskToUser(taskId, request),
                TaskDTO.class
        ), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PutMapping(path = TASK_ASSIGNEE,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("Change task assignee.")
    public ResponseEntity<TaskDTO> changeTaskAssignee(
            @PathVariable("taskId") final UUID taskId,
            @PathVariable("assigneeId") UUID userId,
            @RequestBody @Valid final UserTaskRequestDTO request) {

        return new ResponseEntity<>(conversionService.convert(
                tasksService.changeTaskAssignee(taskId, userId, request),
                TaskDTO.class
        ), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DeleteMapping(path = TASK_ASSIGNEE)
    @ApiOperation("Delete task assignee.")
    public ResponseEntity<TaskDTO> deleteTaskAssignee(
            @PathVariable("taskId") final UUID taskId,
            @PathVariable("assigneeId") final UUID userId) {

        return new ResponseEntity<>(conversionService.convert(
                tasksService.deleteTaskAssignee(taskId, userId),
                TaskDTO.class
        ), OK);
    }
}
