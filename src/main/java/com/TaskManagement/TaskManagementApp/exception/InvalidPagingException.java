package com.TaskManagement.TaskManagementApp.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidPagingException extends Exception {
    private ExceptionDetails details;

    public InvalidPagingException(String message, ExceptionDetails details) {
       super(message);
       setDetails(details);
    }

    public InvalidPagingException(String message, ExceptionDetails details, Throwable e) {
       super(message, e);
       setDetails(details);
    }
}