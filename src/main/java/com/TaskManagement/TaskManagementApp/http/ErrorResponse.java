package com.TaskManagement.TaskManagementApp.http;

import com.TaskManagement.TaskManagementApp.dto.ServiceErrorDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private int code;
    private String message;
    private List<ServiceErrorDTO> errors;


}
