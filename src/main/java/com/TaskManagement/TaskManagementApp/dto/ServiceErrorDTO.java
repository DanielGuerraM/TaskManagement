package com.TaskManagement.TaskManagementApp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceErrorDTO {
    private String code;
    private String message;

    public ServiceErrorDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
