package com.TaskManagement.TaskManagementApp.exception;

public class UserNotFoundException extends Exception {
    private ExceptionDetails details;

    public UserNotFoundException(String message, ExceptionDetails details) {
        super(message);
        setDetails(details);
    }
    public UserNotFoundException(String message, ExceptionDetails details, Throwable e) {
        super(message, e);
        setDetails(details);
    }

    public ExceptionDetails getDetails() {
        return details;
    }

    public void setDetails(ExceptionDetails details) {
        this.details = details;
    }
}