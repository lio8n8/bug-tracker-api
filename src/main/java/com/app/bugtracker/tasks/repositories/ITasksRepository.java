package com.app.bugtracker.tasks.repositories;

import com.app.bugtracker.tasks.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface ITasksRepository extends JpaRepository<Task, UUID> {

    /**
     * Finds all tasks.
     * @param pageable request.
     * @return {@link Page} with list of {@link Task}.
     */
    Page<Task> findAll(Pageable pageable);

    /**
     * Finds tasks by user id.
     *
     * @param id user id
     * @return list of {@link Task}
     */
    Collection<Task> findByAssigneeId(UUID id);
}
