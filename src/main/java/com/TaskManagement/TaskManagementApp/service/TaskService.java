package com.TaskManagement.TaskManagementApp.service;

import com.TaskManagement.TaskManagementApp.dto.CreateTaskDTO;
import com.TaskManagement.TaskManagementApp.dto.UpdateTaskDTO;
import com.TaskManagement.TaskManagementApp.entity.Category;
import com.TaskManagement.TaskManagementApp.entity.Task;
import com.TaskManagement.TaskManagementApp.entity.User;
import com.TaskManagement.TaskManagementApp.exception.CategoryNotFoundException;
import com.TaskManagement.TaskManagementApp.exception.ExceptionDetails;
import com.TaskManagement.TaskManagementApp.exception.TaskNotFoundException;
import com.TaskManagement.TaskManagementApp.exception.UserNotFoundException;
import com.TaskManagement.TaskManagementApp.repository.CategoryRepository;
import com.TaskManagement.TaskManagementApp.repository.TaskRepository;
import com.TaskManagement.TaskManagementApp.repository.UserRepository;
import com.TaskManagement.TaskManagementApp.utils.IdGeneratorService;
import com.TaskManagement.TaskManagementApp.utils.IdTypes;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final IdGeneratorService idGeneratorService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, IdGeneratorService idGeneratorService, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.idGeneratorService = idGeneratorService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<Task> retrieveAllTasks(int page, int perPage) {
        PageRequest paging = PageRequest.of(page - 1, perPage);
        return this.taskRepository.findAll(paging);
    }

    public Task retrieveASingleTask(long id) throws TaskNotFoundException {
        return this.taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("An attempt was made to search for a task by an id that is not registered in the database",
                        new ExceptionDetails(HttpStatus.NOT_FOUND.value(), "The task you are trying to find does not exist"))
        );
    }

    public Task createANewTask(CreateTaskDTO task) throws UserNotFoundException, CategoryNotFoundException {
        User existingUser = this.userRepository.findById(task.getUser_id()).orElseThrow(
                () -> new UserNotFoundException("An attempt was made to search for a user by an id that is not registered in the database",
                        new ExceptionDetails(HttpStatus.NOT_FOUND.value(), "The user you are trying to find does not exist"))
        );

        Category existingCategory = this.categoryRepository.findById(task.getCategory_id()).orElseThrow(
                () -> new CategoryNotFoundException("An attempt was made to search for a category by an id that is not registered in the database",
                        new ExceptionDetails(HttpStatus.NOT_FOUND.value(), "The category you are trying to find does not exist"))
        );

        Task newtask = new Task.builder()
                .id(idGeneratorService.generateId(IdTypes.TASK))
                .title(task.getTitle())
                .description(task.getDescription())
                .user(existingUser)
                .category(existingCategory)
                .isCompleted(false)
                .build();

        return this.taskRepository.save(newtask);
    }

    public Task updateTask(long id, UpdateTaskDTO task) throws TaskNotFoundException {
        Task existingTask = this.taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("An attempt was made to search for a task by an id that is not registered in the database",
                        new ExceptionDetails(HttpStatus.NOT_FOUND.value(), "The task you are trying to update does not exist"))
        );

        return this.taskRepository.save(update(existingTask, task));
    }

    public void deleteTask(long id) throws TaskNotFoundException {
        Task existingTask = this.taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("An attempt was made to search for a task by an id that is not registered in the database",
                        new ExceptionDetails(HttpStatus.NOT_FOUND.value(), "The task you are trying to delete does not exist"))
        );

        this.taskRepository.delete(existingTask);
    }

    public Task update(Task target, UpdateTaskDTO source) {
        if(source.getTitle() != null) {
            target.setTitle(source.getTitle());
        }

        if(source.getDescription() != null) {
            target.setDescription(source.getDescription());
        }

        if(source.isCompleted() != target.is_completed()) {
            target.set_completed(source.isCompleted());
        }

        return target;
    }
}
