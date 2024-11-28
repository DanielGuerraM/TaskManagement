package com.TaskManagement.TaskManagementApp.dto;

import com.TaskManagement.TaskManagementApp.entity.Category;
import com.TaskManagement.TaskManagementApp.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateTaskDTO {
    @NotNull(message = "The title field is required")
    @NotBlank(message = "The title field cannot be empty")
    private String title;
    @NotNull(message = "The description field is required")
    @NotBlank(message = "The description field cannot be empty")
    private String description;
    @NotNull(message = "The category_id field is required")
    @NotBlank(message = "The category_id field cannot be empty")
    private long category_id;
    @NotNull(message = "The user_id field is required")
    @NotBlank(message = "The user_id field cannot be empty")
    private long user_id;
}
