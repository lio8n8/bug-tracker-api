package com.app.bugtracker.tasks.models;

import com.app.bugtracker.users.models.User;
import com.app.bugtracker.projects.models.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.time.Instant;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

/**
 * Task entity.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    /**
     * Task id.
     */
    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID id;

    /**
     * Task title.
     */
    private String title;

    /**
     * Task description.
     */
    private String description;

    /**
     * Task type.
     */
    @Enumerated(EnumType.STRING)
    private Type type;

    /**
     * Task priority.
     */
    @Enumerated(EnumType.STRING)
    private Priority priority;

    /**
     * Task status.
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * Task estimate.
     */
    private Integer estimate;

    /**
     * Task creation time.
     */
    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant createdAt;

    /**
     * Time of task updating.
     */
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant updatedAt;

    /**
     * User, who updated the task.
     */
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    /**
     * User, who created the task.
     */
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    /**
     * Project.
     */
    @ManyToOne
    private Project project;

    /**
     * The user to whom the task was assigned.
     */
    @ManyToOne
    @JoinColumn(name = "assignee")
    private User assignee;
}
