package com.app.bugtracker.exceptions;

public enum Exceptions {
    USER_NOT_FOUND(0, "User not found."),
    TASK_NOT_FOUND(1, "Task not found."),
    PROJECT_NOT_FOUND(2, "Project not found."),
    COMPANY_NOT_FOUND(3, "Company not found."),
    ERROR_GET_USERNAME_FROM_CONTEXT(4, "Not possible get username from context.");

    private final int code;
    private final String message;

    Exceptions(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
