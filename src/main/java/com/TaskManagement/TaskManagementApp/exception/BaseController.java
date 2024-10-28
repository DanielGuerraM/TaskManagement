package com.TaskManagement.TaskManagementApp.exception;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class.getName());

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ExceptionDetails> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException e) {
        LOG.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(e.getDetails());
    }
}