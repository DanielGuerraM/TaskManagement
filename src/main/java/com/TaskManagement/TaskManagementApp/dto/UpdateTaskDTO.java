package com.TaskManagement.TaskManagementApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskDTO {
    private String title;
    private String description;
    private boolean completed;
}
