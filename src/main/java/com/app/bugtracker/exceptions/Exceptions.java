package com.app.bugtracker.exceptions;

public enum Exceptions {
    USER_NOT_FOUND(0, "User not found."),
    TASK_NOT_FOUND(1, "Task not found.");

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
