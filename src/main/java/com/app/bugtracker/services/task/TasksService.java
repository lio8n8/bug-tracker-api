package com.app.bugtracker.services.task;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.app.bugtracker.DTO.task.CreateTaskDTO;
import com.app.bugtracker.models.Task;
import com.app.bugtracker.repositories.ITasksRepository;

@Service
public class TasksService implements ITasksService {

    private final ITasksRepository tasksRepository;

    @Autowired
    public TasksService(final ITasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    /**
     * Find task by id.
     * 
     * @param id task id
     * @return {@link Task}
     */
    @Override
    public Optional<Task> findById(final UUID id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Task> findAll(final Integer skip, final Integer limit) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Create task.
     * 
     * @param createTaskDTO
     * @return {@link Task}
     */
    @Override
    public Task create(final CreateTaskDTO createTaskDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Update task.
     * 
     * @param id
     * @param createTaskDTO
     * @return {@link Task}
     */
    @Override
    public Task update(final UUID id, final CreateTaskDTO createTaskDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Delete task by id.
     * 
     * @param id task id
     */
    @Override
    public void deleteById(final UUID id) {
        // TODO Auto-generated method stub

    }

}
