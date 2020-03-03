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
     * Projects root url.
     */
    String PROJECTS = "/api/projects";

    /**
     * Project by id.
     */
    String PROJECT = PROJECTS + "/{id}";

    /**
     * Companies root url.
     */
    String COMPANIES = "/api/companies";

    /**
     * Company by id.
     */
    String COMPANY = COMPANIES + "/{id}";
}
