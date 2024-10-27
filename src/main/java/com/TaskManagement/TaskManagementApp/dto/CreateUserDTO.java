package com.TaskManagement.TaskManagementApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO {
    @NotNull(message = "The name field is required")
    @NotBlank(message = "The name field cannot be empty")
    private String name;
    @NotNull(message = "The last_name field is required")
    @NotBlank(message = "The last_name field cannot be empty")
    private String last_name;
    @NotNull(message = "The email field is required")
    @NotBlank(message = "The email field cannot be empty")
    @Email(message = "The entered value has an incorrect format")
    private String email;
}
