package com.app.bugtracker.services.task;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.bugtracker.dto.task.CreateTaskDTO;
import com.app.bugtracker.models.task.Task;
import com.app.bugtracker.models.user.User;
import com.app.bugtracker.repositories.ITasksRepository;
import com.app.bugtracker.repositories.IUsersRepository;
import com.app.bugtracker.exceptions.Exceptions;
import com.app.bugtracker.exceptions.NotFoundException;

@Service
public class TasksService implements ITasksService {

    private final ITasksRepository tasksRepository;
    private final IUsersRepository usersRepository;

    @Autowired
    public TasksService(final ITasksRepository tasksRepository,
        final IUsersRepository usersRepository) {
        this.tasksRepository = tasksRepository;
        this.usersRepository = usersRepository;
    }

    /**
     * Find task by id.
     * 
     * @param id task id
     * @return {@link Task}
     */
    @Override
    public Task findById(final UUID id) {
          return tasksRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Exceptions.USER_NOT_FOUND));
    }

    @Override
    public Page<Task> findAll(final Integer skip, final Integer limit) {
        return tasksRepository.findAll(PageRequest.of(skip, limit));
    }
    
    /**
     * Create task.
     * 
     * @param createTaskDTO
     * @return {@link Task}
     */
    @Override
    public Task create(final CreateTaskDTO createTaskDTO) {
        // TODO: Get createdBy from token.
        /*User createdBy = usersRepository.findById(createTaskDTO.getCreatedBy())
                .orElseThrow(() -> new NotFoundException(Exceptions.USER_NOT_FOUND));*/

        User assignedTo = usersRepository.findById(createTaskDTO.getAssignedTo())
                .orElseThrow(() -> new NotFoundException(Exceptions.USER_NOT_FOUND));

        Task task = Task.builder()
            .title(createTaskDTO.getTitle())
            .description(createTaskDTO.getDescription())
            .priority(createTaskDTO.getPriority())
            .type(createTaskDTO.getType())
            .status(createTaskDTO.getStatus())
            .assignedTo(assignedTo)
            .created(Instant.now())
            .updated(Instant.now())
            .build();

        return tasksRepository.save(task);
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
        Task task = tasksRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Exceptions.TASK_NOT_FOUND));

        User assignedTo = usersRepository.findById(createTaskDTO.getAssignedTo())
                .orElseThrow(() -> new NotFoundException(Exceptions.USER_NOT_FOUND));

        task.setTitle(createTaskDTO.getTitle());
        task.setDescription(createTaskDTO.getDescription());
        task.setPriority(createTaskDTO.getPriority());
        task.setType(createTaskDTO.getType());
        task.setStatus(createTaskDTO.getStatus());
        task.setAssignedTo(assignedTo);
        task.setUpdated(Instant.now());

        return tasksRepository.save(task);
    }

    /**
     * Delete task by id.
     * 
     * @param id task id
     */
    @Override
    public void deleteById(final UUID id) {
        tasksRepository.deleteById(id);
    }

}
