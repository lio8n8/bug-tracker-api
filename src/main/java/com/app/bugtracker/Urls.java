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
}
