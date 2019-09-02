package com.app.bugtracker.models;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Column;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project entity
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    private String title;
    
    private String description;
    
    @Column(name = "img_path")
    private String imgPath;
    
    @Column(name = "created_at")
    @Type(type = "java.time.Instant")
    private Instant createdAt;
    
    @Column(name = "updated_at")
    @Type(type = "java.time.Instant")
    private Instant updatedAt;
    
    @ManyToOne
    private User createdBy;
    
    @ManyToOne
    private User updatedBy;
    
    @OneToMany
    private Set<Task> tasks;
}
