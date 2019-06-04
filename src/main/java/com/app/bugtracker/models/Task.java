package com.app.bugtracker.models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.app.bugtracker.constants.Priority;
import com.app.bugtracker.constants.TaskStatus;
import com.app.bugtracker.constants.TaskType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User entity
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private String title;
	
	private String description;
	
	private Priority priority;
	
	private TaskType type;
	
	private TaskStatus status;
	
	@ManyToOne
	private User createdBy;
	
	@ManyToOne
	private User assignedTo;
	
	private Date created;
	
	private Date updated;
}
