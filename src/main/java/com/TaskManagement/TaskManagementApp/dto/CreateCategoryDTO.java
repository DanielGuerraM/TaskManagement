package com.TaskManagement.TaskManagementApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryDTO {
    @NotNull(message = "The name field is required")
    @NotBlank(message = "The name field cannot be empty")
    private String name;
}