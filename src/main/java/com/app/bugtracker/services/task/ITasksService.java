package com.app.bugtracker.services.task;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.app.bugtracker.dto.task.CreateTaskDTO;
import com.app.bugtracker.models.Task;

/**
 * Tasks service interface
 */
public interface ITasksService {

    /**
     * Find task by id.
     * 
     * @param id task id
     * @return {@link Task}
     */
    Task findById(UUID id);

    Page<Task> findAll(Integer skip, Integer limit);

    /**
     * Create task.
     * 
     * @param createTaskDTO
     * @return {@link Task}
     */
    Task create(CreateTaskDTO createTaskDTO);

    /**
     * Update task.
     * 
     * @param id
     * @param createTaskDTO
     * @return {@link Task}
     */
    Task update(UUID id, CreateTaskDTO createTaskDTO);

    /**
     * Delete task by id.
     * 
     * @param id task id
     */
    void deleteById(UUID id);
}
