package com.app.bugtracker.tasks.services;

import com.app.bugtracker.auth.services.IAuthContext;
import com.app.bugtracker.exceptions.NotFoundException;
import com.app.bugtracker.projects.services.IProjectsService;
import com.app.bugtracker.tasks.dto.TaskRequest;
import com.app.bugtracker.tasks.dto.UserTaskRequestDTO;
import com.app.bugtracker.tasks.models.Priority;
import com.app.bugtracker.tasks.models.Status;
import com.app.bugtracker.tasks.models.Task;
import com.app.bugtracker.tasks.repositories.ITasksRepository;
import com.app.bugtracker.users.models.User;
import com.app.bugtracker.projects.models.Project;
import com.app.bugtracker.users.services.IUsersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
     * Users service.
     */
    private final IUsersService usersService;

    /**
     * Authentication context.
     */
    private final IAuthContext authContext;

    public TasksService(final ITasksRepository tasksRepository,
                        final IProjectsService projectsService,
                        final IUsersService usersService,
                        final IAuthContext authContext) {
        this.tasksRepository = tasksRepository;
        this.projectsService = projectsService;
        this.usersService = usersService;
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
    public Page<Task> findByAssigneeId(final UUID id, final Pageable request) {
        return tasksRepository.findByAssigneeId(id, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Task> findByCurrentUser(Pageable request) {
        User user = authContext.getUser();

        return tasksRepository.findByAssigneeId(user.getId(), request);
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
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
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
        task.setUpdatedAt(Instant.now());
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Task assignTaskToUser(UUID taskId, UserTaskRequestDTO request) {
        Task task = findById(taskId);
        User assignee = usersService.findById(request.getUserId());

        task.setAssignee(assignee);

        return tasksRepository.save(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task changeTaskAssignee(UUID taskId, UUID userId, UserTaskRequestDTO request) {
        Task task = findById(taskId);
        User assignee = usersService.findById(userId);
        User newAssignee = usersService.findById(request.getUserId());

        task.setAssignee(newAssignee);

        return tasksRepository.save(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task deleteTaskAssignee(UUID taskId, UUID userId) {
        Task task = findById(taskId);
        usersService.findById(userId);

        task.setAssignee(null);

        return tasksRepository.save(task);
    }
}
