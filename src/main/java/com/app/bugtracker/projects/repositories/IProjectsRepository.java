package com.app.bugtracker.projects.repositories;

import com.app.bugtracker.projects.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Projects repository.
 */
@Repository
public interface IProjectsRepository extends JpaRepository<Project, UUID> {
}
