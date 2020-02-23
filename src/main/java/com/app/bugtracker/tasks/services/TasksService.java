package com.app.bugtracker.tasks.services;

import com.app.bugtracker.auth.services.IAuthContext;
import com.app.bugtracker.exceptions.NotFoundException;
import com.app.bugtracker.tasks.dto.TaskRequest;
import com.app.bugtracker.tasks.models.Priority;
import com.app.bugtracker.tasks.models.Status;
import com.app.bugtracker.tasks.models.Task;
import com.app.bugtracker.tasks.repositories.ITasksRepository;
import com.app.bugtracker.users.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.app.bugtracker.exceptions.Exceptions.TASK_NOT_FOUND;

/**
 * Task service implementation.
 */
@Service
public class TasksService implements ITasksService {

    /**
     * Tasks repository.
     */
    private ITasksRepository tasksRepository;

    /**
     * Authentication context.
     */
    private IAuthContext authContext;

    public TasksService(ITasksRepository tasksRepository,
                        IAuthContext authContext) {
        this.tasksRepository = tasksRepository;
        this.authContext = authContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task findById(UUID id) {
        return tasksRepository.findById(id).orElseThrow(
                () -> new NotFoundException(TASK_NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Task> findAll(Pageable request) {
        return tasksRepository.findAll(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task create(TaskRequest request) {
        User user = authContext.getUser();

        return tasksRepository.save(
                Task.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .type(request.getType())
                    .priority(request.getPriority() != null ? request.getPriority() : Priority.TRIVIAL)
                    .status(Status.NEW)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .createdBy(user)
                    .updatedBy(user)
                .build()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task update(UUID id, TaskRequest request) {
        Task task = findById(id);

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setType(request.getType());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setUpdatedAt(LocalDateTime.now());
        task.setUpdatedBy(authContext.getUser());

        return tasksRepository.save(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task patch(UUID id, TaskRequest request) {
        throw new RuntimeException("Not yet implemented!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(UUID id) {
        tasksRepository.deleteById(id);
    }
}
