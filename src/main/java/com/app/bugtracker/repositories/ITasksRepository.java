package com.app.bugtracker.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.bugtracker.models.Task;

@Repository
public interface ITasksRepository extends JpaRepository<Task, UUID> {

}
