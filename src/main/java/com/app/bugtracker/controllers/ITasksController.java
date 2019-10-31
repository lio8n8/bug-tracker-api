package com.app.bugtracker.controllers;

import java.util.List;
import java.util.UUID;

import com.app.bugtracker.dto.task.AssignTaskRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.app.bugtracker.models.task.TaskPriority;
import com.app.bugtracker.models.task.TaskStatus;
import com.app.bugtracker.models.task.TaskType;
import com.app.bugtracker.dto.task.CreateTaskDTO;
import com.app.bugtracker.dto.task.TaskDTO;

public interface ITasksController {
    
    /**
     * Find task by id.
     * @param id task id
     *
     * @return {@link TaskDTO}
     */
    ResponseEntity<TaskDTO> findById(UUID id);
    
    /**
     * Find all tasks.
     * @param skip
     * @param limit
     *
     * @return {@link Page}
     */
    ResponseEntity<Page<TaskDTO>> findAll(Integer skip, Integer limit);
    
    /**
     * Create task.
     * @param createTaskDTO task data
     *
     * @return {@link TaskDTO}
     */
    ResponseEntity<TaskDTO> create(CreateTaskDTO createTaskDTO);
    
    /**
     * Update task.
     * @param id task id
     * @param createTaskDTO task data
     *
     * @return updated {@link TaskDTO}
     */
    ResponseEntity<TaskDTO> update(UUID id, CreateTaskDTO createTaskDTO);
    
    /**
     * Delete task by id.
     * @param id task id
     */
    ResponseEntity<Void> deleteById(UUID id);
    
    /**
     * Get all possible task statuses.
     *
     * @return {@link TaskStatus}
     */
    ResponseEntity<List<TaskStatus>> getTaskStatuses();
    
    /**
     * Get all possible task types.
     *
     * @return {@link TaskType}
     */
    ResponseEntity<List<TaskType>> getTaskTypes();
    
    /**
     * Get all possible task priorities.
     *
     * @return {@link TaskPriority}
     */
    ResponseEntity<List<TaskPriority>> getTaskPriorities();

    /**
     * Assign task to user.
     * @param assignTaskRequest request.
     *
     * @return {@link TaskDTO}.
     */
    ResponseEntity<TaskDTO> assignTask(AssignTaskRequest assignTaskRequest);
}
