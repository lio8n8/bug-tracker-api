package com.app.bugtracker.projects.models;

import com.app.bugtracker.tasks.models.Task;
import com.app.bugtracker.users.models.User;
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
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

/**
 * Project entity.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    /**
     * Project id.
     */
    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID id;

    /**
     * Project title.
     */
    private String title;

    /**
     * Project description.
     */
    private String description;

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

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    @ManyToMany
    @JoinTable(name = "users_projects")
    private Set<User> team;
}
