package com.TaskManagement.TaskManagementApp.controller;

import com.TaskManagement.TaskManagementApp.dto.CreateTaskDTO;
import com.TaskManagement.TaskManagementApp.dto.UpdateTaskDTO;
import com.TaskManagement.TaskManagementApp.entity.Task;
import com.TaskManagement.TaskManagementApp.exception.CategoryNotFoundException;
import com.TaskManagement.TaskManagementApp.exception.InvalidPagingException;
import com.TaskManagement.TaskManagementApp.exception.TaskNotFoundException;
import com.TaskManagement.TaskManagementApp.exception.UserNotFoundException;
import com.TaskManagement.TaskManagementApp.http.Paging;
import com.TaskManagement.TaskManagementApp.http.PagingResponse;
import com.TaskManagement.TaskManagementApp.service.TaskService;
import com.TaskManagement.TaskManagementApp.utils.ErrorResponseService;
import com.TaskManagement.TaskManagementApp.utils.PagingValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/task")
public class TaskController {
    private final TaskService taskService;
    private final PagingValidationService pagingValidationService;

    @Autowired
    public TaskController(TaskService taskService, PagingValidationService pagingValidationService) {
        this.taskService = taskService;
        this.pagingValidationService = pagingValidationService;
    }

    @GetMapping
    public ResponseEntity<Object> retrieveAllTasks(@RequestParam(name = "page", defaultValue = "1") String page, @RequestParam(name = "per_page", defaultValue = "50") String perPage) throws InvalidPagingException {
        pagingValidationService.ValidatePagingParams(page, perPage);

        Page<Task> tasks = this.taskService.retrieveAllTasks(Integer.parseInt(page), Integer.parseInt(perPage));

        Paging paging = new Paging();
        paging.setPage(Integer.parseInt(page));
        paging.setPer_page(Integer.parseInt(perPage));
        paging.setTotal_pages(tasks.getTotalPages());
        paging.setTotal_items(tasks.getTotalElements());

        PagingResponse response = new PagingResponse();
        response.setData(tasks.getContent());
        response.setPaging(paging);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "{idTask}")
    public ResponseEntity<Object> retrieveASingleTask(@PathVariable("idTask") String idTask) throws TaskNotFoundException {
        return ResponseEntity.ok(this.taskService.retrieveASingleTask(Long.parseLong(idTask)));
    }

    @PostMapping
    public ResponseEntity<Object> createANewTask(@RequestBody CreateTaskDTO task, BindingResult result) throws UserNotFoundException, CategoryNotFoundException {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorResponseService.GenerateErrorResponse(result));
        }

        return ResponseEntity.ok(this.taskService.createANewTask(task));
    }

    @PatchMapping(path = "{idTask}")
    public ResponseEntity<Object> updateTask(@PathVariable("idTask") String idTask, @RequestBody UpdateTaskDTO task) throws TaskNotFoundException {
        return ResponseEntity.ok(this.taskService.updateTask(Long.parseLong(idTask), task));
    }

    @DeleteMapping(path = "{idTask}")
    public ResponseEntity<Object> deleteTask(@PathVariable("idTask") String idTask) throws TaskNotFoundException {
        this.taskService.deleteTask(Long.parseLong(idTask));

        return ResponseEntity.noContent().build();
    }
}