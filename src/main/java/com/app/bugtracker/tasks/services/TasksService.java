package com.app.bugtracker.tasks.services;

import com.app.bugtracker.auth.services.IAuthContext;
import com.app.bugtracker.exceptions.NotFoundException;
import com.app.bugtracker.projects.services.IProjectsService;
import com.app.bugtracker.tasks.dto.TaskRequest;
import com.app.bugtracker.tasks.models.Priority;
import com.app.bugtracker.tasks.models.Status;
import com.app.bugtracker.tasks.models.Task;
import com.app.bugtracker.tasks.repositories.ITasksRepository;
import com.app.bugtracker.users.models.User;
import com.app.bugtracker.projects.models.Project;
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
    private final ITasksRepository tasksRepository;

    /**
     * Projects service.
     */
    private final IProjectsService projectsService;

    /**
     * Authentication context.
     */
    private final IAuthContext authContext;

    public TasksService(final ITasksRepository tasksRepository,
                        final IProjectsService projectsService,
                        final IAuthContext authContext) {
        this.tasksRepository = tasksRepository;
        this.projectsService = projectsService;
        this.authContext = authContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task findById(final UUID id) {
        return tasksRepository.findById(id).orElseThrow(
                () -> new NotFoundException(TASK_NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Task> findAll(final Pageable request) {
        return tasksRepository.findAll(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task create(final TaskRequest request) {
        User user = authContext.getUser();
        Project project = projectsService.findById(request.getProjectId());

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
                    .project(project)
                .build()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task update(final UUID id, final TaskRequest request) {
        Task task = findById(id);
        Project project = projectsService.findById(request.getProjectId());

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setType(request.getType());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setUpdatedAt(LocalDateTime.now());
        task.setUpdatedBy(authContext.getUser());
        task.setProject(project);

        return tasksRepository.save(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task patch(final UUID id, final TaskRequest request) {
        throw new RuntimeException("Not yet implemented!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(final UUID id) {
        tasksRepository.deleteById(id);
    }
}
