package com.TaskManagement.TaskManagementApp.exception;

public class CategoryAlreadyRegisteredException extends Exception {
    private ExceptionDetails details;

    public CategoryAlreadyRegisteredException(String message, ExceptionDetails details) {
        super(message);
        setDetails(details);
    }

    public CategoryAlreadyRegisteredException(String message, ExceptionDetails details, Throwable e) {
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