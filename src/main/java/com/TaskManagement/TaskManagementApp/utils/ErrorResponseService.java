package com.TaskManagement.TaskManagementApp.utils;

import com.TaskManagement.TaskManagementApp.dto.ServiceErrorDTO;
import com.TaskManagement.TaskManagementApp.http.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

public class ErrorResponseService {
    public static ErrorResponse GenerateErrorResponse(BindingResult result){
        ErrorResponse response = new ErrorResponse();

        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Errors have occurred with validations");

        List<ServiceErrorDTO> errorList = result.getAllErrors().stream()
                .map(error -> new ServiceErrorDTO(error.getCode(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        response.setErrors(errorList);

        return response;
    }
}
