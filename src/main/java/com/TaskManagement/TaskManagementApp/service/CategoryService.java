package com.TaskManagement.TaskManagementApp.service;

import com.TaskManagement.TaskManagementApp.dto.CreateCategoryDTO;
import com.TaskManagement.TaskManagementApp.entity.Category;
import com.TaskManagement.TaskManagementApp.exception.CategoryAlreadyRegisteredException;
import com.TaskManagement.TaskManagementApp.exception.ExceptionDetails;
import com.TaskManagement.TaskManagementApp.repository.CategoryRepository;
import com.TaskManagement.TaskManagementApp.utils.IdGeneratorService;
import com.TaskManagement.TaskManagementApp.utils.IdTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private IdGeneratorService idGeneratorService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, IdGeneratorService idGeneratorService) {
        this.categoryRepository = categoryRepository;
        this.idGeneratorService = idGeneratorService;
    }

    public Category createANewCategory(CreateCategoryDTO category) throws CategoryAlreadyRegisteredException {
        String lowerName = category.getName().toLowerCase();

        Optional<Category> existingCategory = this.categoryRepository.findByName(lowerName);

        if(existingCategory.isPresent()) {
            throw new CategoryAlreadyRegisteredException("An attempt was made to register a category already existing in the database.",
                    new ExceptionDetails(HttpStatus.BAD_REQUEST.value(), "The category you are trying to create already exists"));
        }

        Category newCateogory = new Category();
        newCateogory.setId(idGeneratorService.generateId(IdTypes.CATEGORY));
        newCateogory.setName(lowerName);

        return this.categoryRepository.save(newCateogory);
    }
}