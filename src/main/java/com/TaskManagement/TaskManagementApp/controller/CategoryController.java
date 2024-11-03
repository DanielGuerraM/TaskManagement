package com.TaskManagement.TaskManagementApp.controller;

import com.TaskManagement.TaskManagementApp.dto.CreateCategoryDTO;
import com.TaskManagement.TaskManagementApp.entity.Category;
import com.TaskManagement.TaskManagementApp.exception.CategoryAlreadyRegisteredException;
import com.TaskManagement.TaskManagementApp.exception.CategoryNotFoundException;
import com.TaskManagement.TaskManagementApp.exception.InvalidPagingException;
import com.TaskManagement.TaskManagementApp.http.Paging;
import com.TaskManagement.TaskManagementApp.http.PagingResponse;
import com.TaskManagement.TaskManagementApp.service.CategoryService;
import com.TaskManagement.TaskManagementApp.utils.ErrorResponseService;
import com.TaskManagement.TaskManagementApp.utils.PagingValidationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final PagingValidationService pagingValidationService;

    @Autowired
    public CategoryController(CategoryService categoryService, PagingValidationService pagingValidationService) {
        this.categoryService = categoryService;
        this.pagingValidationService = pagingValidationService;
    }

    @PostMapping
    public ResponseEntity<Object> createANewCategory(@RequestBody @Valid CreateCategoryDTO categoryDTO, BindingResult result) throws CategoryAlreadyRegisteredException {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorResponseService.GenerateErrorResponse(result));
        }

        return ResponseEntity.ok(this.categoryService.createANewCategory(categoryDTO));
    }

    @GetMapping
    public ResponseEntity<Object> retrieveAllCategories(@RequestParam(value = "page", defaultValue = "1") String page, @RequestParam(value = "per_page", defaultValue = "50") String perPage) throws InvalidPagingException {
        pagingValidationService.ValidatePagingParams(page, perPage);

        Page<Category> categories = this.categoryService.retrieveAllCategories(Integer.parseInt(page), Integer.parseInt(perPage));

        Paging paging = new Paging();
        paging.setPage(Integer.parseInt(page));
        paging.setPer_page(Integer.parseInt(perPage));
        paging.setTotal_pages(categories.getTotalPages());
        paging.setTotal_items(categories.getTotalElements());

        PagingResponse response = new PagingResponse();
        response.setData(categories.getContent());
        response.setPaging(paging);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "{categoryId}")
    public ResponseEntity<Object> retrieveASingleCategory(@PathVariable("categoryId") String categoryId) throws CategoryNotFoundException {
        return ResponseEntity.ok(this.categoryService.retrieveASingleCategory(Long.parseLong(categoryId)));
    }
}