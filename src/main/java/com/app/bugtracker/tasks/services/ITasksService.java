package com.app.bugtracker.tasks.services;

import com.app.bugtracker.tasks.dto.TaskRequest;
import com.app.bugtracker.tasks.dto.UserTaskRequestDTO;
import com.app.bugtracker.tasks.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Tasks service interface.
 */
public interface ITasksService {

    /**
     * Finds task by id.
     *
     * @param id task id
     * @return {@link Task}
     */
    Task findById(UUID id);

    /**
     * Finds all tasks.
     *
     * @param request pageable request
     * @return {@link Page} with list of tasks
     */
    Page<Task> findAll(Pageable request);

    /**
     * Finds tasks by assignee id.
     *
     * @param request pageable request
     * @param id user id
     * @return list of {@link Task}
     */
    Page<Task> findByAssigneeId(UUID id, Pageable request);

    /**
     * Finds all tasks where current user is assignee.
     *
     * @param request pageable request
     * @return {@link Page} of {@link Task}
     */
    Page<Task> findByCurrentUser(Pageable request);

    /**
     * Creates a new task.
     *
     * @param request create task request
     * @return {@link Task}
     */
    Task create(TaskRequest request);

    /**
     * Updates task.
     *
     * @param id task id
     * @param request update task request
     * @return {@link Task}
     */
    Task update(UUID id, TaskRequest request);

    /**
     * Patches task.
     *
     * @param id task id
     * @param request patch task request
     * @return {@link Task}
     */
    Task patch(UUID id, TaskRequest request);

    /**
     * Deletes task by id.
     *
     * @param id task id
     */
    void deleteById(UUID id);

    /**
     * Assigns task to user.
     *
     * @param taskId task id
     * @param request request with assignee id
     *
     * @return {@link Task}
     */
    Task assignTaskToUser(UUID taskId, UserTaskRequestDTO request);

    /**
     * Changes task assignee.
     *
     * @param taskId task id
     * @param userId user id
     * @param request request with new assignee id
     *
     * @return {@link Task}
     */
    Task changeTaskAssignee(UUID taskId, UUID userId, UserTaskRequestDTO request);

    /**
     * Deletes assignee from task.
     *
     * @param taskId task id
     * @param userId user id
     *
     * @return {@link Task}
     */
    Task deleteTaskAssignee(UUID taskId, UUID userId);
}
