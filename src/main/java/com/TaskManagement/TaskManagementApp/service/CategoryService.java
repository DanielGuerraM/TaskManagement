package com.TaskManagement.TaskManagementApp.service;

import com.TaskManagement.TaskManagementApp.dto.CreateCategoryDTO;
import com.TaskManagement.TaskManagementApp.dto.UpdateCategoryDTO;
import com.TaskManagement.TaskManagementApp.entity.Category;
import com.TaskManagement.TaskManagementApp.exception.CategoryAlreadyRegisteredException;
import com.TaskManagement.TaskManagementApp.exception.CategoryNotFoundException;
import com.TaskManagement.TaskManagementApp.exception.ExceptionDetails;
import com.TaskManagement.TaskManagementApp.repository.CategoryRepository;
import com.TaskManagement.TaskManagementApp.utils.IdGeneratorService;
import com.TaskManagement.TaskManagementApp.utils.IdTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final IdGeneratorService idGeneratorService;

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

        Category newCategory = new Category();
        newCategory.setId(idGeneratorService.generateId(IdTypes.CATEGORY));
        newCategory.setName(lowerName);

        return this.categoryRepository.save(newCategory);
    }

    public Page<Category> retrieveAllCategories(int page, int perPage) {
        PageRequest paging = PageRequest.of(page - 1, perPage);

        return this.categoryRepository.findAll(paging);
    }

    public Category retrieveASingleCategory(long categoryId) throws CategoryNotFoundException {
        return this.categoryRepository.findById(categoryId).orElseThrow(
                () -> new CategoryNotFoundException("You are trying to search for a category that does not exist in the database",
                        new ExceptionDetails(HttpStatus.NOT_FOUND.value(), "The category you are trying to find does not exist"))
        );
    }

    public Category updateCategory(long id, UpdateCategoryDTO category) throws CategoryNotFoundException {
        Category existingCategory = this.categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("You are trying to search for a category that does not exist in the database",
                        new ExceptionDetails(HttpStatus.NOT_FOUND.value(), "The category you are trying to find does not exist"))
        );

        if(category.getName() != null) {
            existingCategory.setName(category.getName());
        }

        return this.categoryRepository.save(existingCategory);
    }

    public void deleteCategory(long id) throws CategoryNotFoundException {
        this.categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("You are trying to search for a category that does not exist in the database",
                        new ExceptionDetails(HttpStatus.NOT_FOUND.value(), "The category you are trying to find does not exist"))
        );

        this.categoryRepository.deleteById(id);
    }
}