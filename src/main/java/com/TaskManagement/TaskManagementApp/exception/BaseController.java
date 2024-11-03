package com.TaskManagement.TaskManagementApp.exception;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class.getName());

    //Not found
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleUserNotFoundException(UserNotFoundException e) {
        LOG.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getDetails());
    }

    //Bad request
    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ExceptionDetails> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException e) {
        LOG.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(e.getDetails());
    }

    @ExceptionHandler(CategoryAlreadyRegisteredException.class)
    public ResponseEntity<ExceptionDetails> handleCategoryAlreadyRegisteredException(CategoryAlreadyRegisteredException e) {
        LOG.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(e.getDetails());
    }
}