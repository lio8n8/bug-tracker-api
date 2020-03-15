package com.app.bugtracker.tasks.controllers;

import com.app.bugtracker.tasks.dto.TaskDTO;
import com.app.bugtracker.tasks.dto.TaskRequest;
import com.app.bugtracker.tasks.dto.UserTaskRequestDTO;
import com.app.bugtracker.tasks.models.Priority;
import com.app.bugtracker.tasks.models.Status;
import com.app.bugtracker.tasks.models.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

/**
 * Tasks controller interface.
 */
public interface ITasksController {

    /**
     * Finds all tasks.
     *
     * @param request pageable request
     * @return {@link Page} with list of tasks.
     */
    ResponseEntity<Page<TaskDTO>> findAll(Pageable request);

    /**
     * Finds task by id.
     *
     * @param id task id
     * @return {@link TaskDTO}
     */
    ResponseEntity<TaskDTO> findById(UUID id);

    /**
     * Finds all possible task types.
     * @return list of task types
     */
    ResponseEntity<List<Type>> findTaskTypes();

    /**
     * Finds all possible task priorities.
     * @return list of task priorities
     */
    ResponseEntity<List<Priority>> findTaskPriorities();

    /**
     * Finds all possible task statuses.
     * @return list of task statuses
     */
    ResponseEntity<List<Status>> findTaskStatuses();

    /**
     * Finds tasks by assignee id.
     *
     * @param id assignee id
     * @param request pageable request
     *
     * @return pages with {@link TaskDTO}
     */
    ResponseEntity<Page<TaskDTO>> findByAssigneeId(UUID id, Pageable request);

    /**
     * Finds tasks assigned to current user.
     *
     * @param request pageable request
     * @return pages with {@link TaskDTO}
     */
    ResponseEntity<Page<TaskDTO>> findByCurrentAssignee(Pageable request);

    /**
     * Creates a new task.
     *
     * @param request create task request
     * @return created {@link TaskDTO}
     */
    ResponseEntity<TaskDTO> create(TaskRequest request);

    /**
     * Updates task.
     *
     * @param id task id
     * @param request update task request
     * @return updated {@link TaskDTO}
     */
    ResponseEntity<TaskDTO> update(UUID id, TaskRequest request);

    /**
     * Patches task.
     *
     * @param id task id
     * @param request patch task request
     * @return {@link TaskDTO}
     */
    ResponseEntity<TaskDTO> patch(UUID id, TaskRequest request);

    /**
     * Deletes task by id.
     *
     * @param id task id
     * @return empty response
     */
    ResponseEntity deleteById(UUID id);

    /**
     * Assigns task to user.
     *
     * @param taskId task id
     * @param request request with assignee id
     *
     * @return {@link TaskDTO}
     */
    ResponseEntity<TaskDTO> assignTaskToUser(UUID taskId, UserTaskRequestDTO request);

    /**
     * Changes task assignee.
     *
     * @param taskId task id
     * @param userId user id
     * @param request request with new assignee id
     *
     * @return {@link TaskDTO}
     */
    ResponseEntity<TaskDTO> changeTaskAssignee(UUID taskId, UUID userId, UserTaskRequestDTO request);

    /**
     * Deletes assignee from task.
     *
     * @param taskId task id
     * @param userId user id
     *
     * @return {@link TaskDTO}
     */
    ResponseEntity<TaskDTO> deleteTaskAssignee(UUID taskId, UUID userId);
}
