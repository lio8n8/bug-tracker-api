package com.app.bugtracker;

/**
 * Urls.
 */
public interface Urls {

    /**
     * Users root url.
     */
    String USERS = "/api/users";

    /**
     * User's url with id.
     */
    String USER = USERS + "/{id}";

    /**
     * Current user.
     */
    String USER_CURRENT = USERS + "/current";

    /**
     * Token url.
     */
    String TOKENS = "/api/tokens";

    /**
     * Tasks root url.
     */
    String TASKS = "/api/tasks";

    /**
     * Task by id.
     */
    String TASK = TASKS + "/{id}";

    /**
     * Task types.
     */
    String TASK_TYPES = TASKS + "/types";

    /**
     * Task priorities.
     */
    String TASK_PRIORITIES = TASKS + "/priorities";

    /**
     * Task statuses.
     */
    String TASK_STATUSES = TASKS + "/statuses";

    /**
     * Tasks for current user.
     */
    String TASKS_FOR_CURRENT_USER = TASKS + "/assignees/me";

    /**
     * Tasks by user id.
     */
    String TASKS_BY_ASSIGNEE = TASKS + "/assignees/{id}";

    /**
     * Task assignees.
     */
    String TASK_ASSIGNEES = TASKS + "/{taskId}" + "/assignees";

    /**
     * Task assignee.
     */
    String TASK_ASSIGNEE = TASK_ASSIGNEES + "/{assigneeId}";

    /**
     * Projects root url.
     */
    String PROJECTS = "/api/projects";

    /**
     * Project by id.
     */
    String PROJECT = PROJECTS + "/{id}";

    /**
     * Project team.
     */
    String PROJECT_TEAM = PROJECT + "/team";

    /**
     * Companies root url.
     */
    String COMPANIES = "/api/companies";

    /**
     * Company by id.
     */
    String COMPANY = COMPANIES + "/{id}";
}
