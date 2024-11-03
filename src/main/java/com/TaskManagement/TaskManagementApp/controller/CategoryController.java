package com.TaskManagement.TaskManagementApp.controller;

import com.TaskManagement.TaskManagementApp.dto.CreateCategoryDTO;
import com.TaskManagement.TaskManagementApp.exception.CategoryAlreadyRegisteredException;
import com.TaskManagement.TaskManagementApp.service.CategoryService;
import com.TaskManagement.TaskManagementApp.utils.ErrorResponseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Object> createANewCategory(@RequestBody @Valid CreateCategoryDTO categoryDTO, BindingResult result) throws CategoryAlreadyRegisteredException {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorResponseService.GenerateErrorResponse(result));
        }

        return ResponseEntity.ok(this.categoryService.createANewCategory(categoryDTO));
    }
}