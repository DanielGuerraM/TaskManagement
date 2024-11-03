package com.TaskManagement.TaskManagementApp.controller;

import com.TaskManagement.TaskManagementApp.dto.CreateUserDTO;
import com.TaskManagement.TaskManagementApp.dto.UpdateUserDTO;
import com.TaskManagement.TaskManagementApp.entity.User;
import com.TaskManagement.TaskManagementApp.exception.EmailAlreadyRegisteredException;
import com.TaskManagement.TaskManagementApp.exception.ExceptionDetails;
import com.TaskManagement.TaskManagementApp.exception.InvalidPagingException;
import com.TaskManagement.TaskManagementApp.exception.UserNotFoundException;
import com.TaskManagement.TaskManagementApp.http.Paging;
import com.TaskManagement.TaskManagementApp.http.PagingResponse;
import com.TaskManagement.TaskManagementApp.service.UserService;
import com.TaskManagement.TaskManagementApp.utils.ErrorResponseService;
import com.TaskManagement.TaskManagementApp.utils.PagingValidationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final UserService userService;
    private final PagingValidationService pagingValidationService;

    @Autowired
    public UserController(UserService userService, PagingValidationService pagingValidationService) {
        this.userService = userService;
        this.pagingValidationService = pagingValidationService;
    }

    @PostMapping
    public ResponseEntity<Object> createANewUser(@RequestBody @Valid CreateUserDTO user, BindingResult result) throws EmailAlreadyRegisteredException {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorResponseService.GenerateErrorResponse(result));
        }

        return ResponseEntity.ok(this.userService.createANewUser(user));
    }

    @GetMapping
    public ResponseEntity<Object> retrieveAllUsers(@RequestParam(name = "page", defaultValue = "1") String page, @RequestParam(name = "per_page", defaultValue = "50") String perPage) throws InvalidPagingException {
        int finalPage = Integer.parseInt(page);
        int finalPerPage = Integer.parseInt(perPage);

        pagingValidationService.ValidatePagingParams(page, perPage);

        Page<User> users = this.userService.retrieveAllUsers(finalPage, finalPerPage);

        Paging paging = new Paging();
        paging.setPage(finalPage);
        paging.setPer_page(finalPerPage);
        paging.setTotal_pages(users.getTotalPages());
        paging.setTotal_items(users.getTotalElements());

        PagingResponse response = new PagingResponse();
        response.setData(users.getContent());
        response.setPaging(paging);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "{userId}")
    public ResponseEntity<User> retrieveASingleUser(@PathVariable("userId") String userId) throws UserNotFoundException {
        return ResponseEntity.ok(this.userService.retrieveASingleUser(Long.parseLong(userId)));
    }

    @PatchMapping(path = "{userId}")
    public ResponseEntity<Object> updateAUser(@PathVariable("userId") String userId, @RequestBody UpdateUserDTO user) throws UserNotFoundException {
        return ResponseEntity.ok(this.userService.updateUser(Long.parseLong(userId), user));
    }

    @DeleteMapping(path = "{userId}")
    public ResponseEntity<Object> deleteAnUser(@PathVariable("userId") String userId) throws UserNotFoundException {
        this.userService.deleteUser(Long.parseLong(userId));

        return ResponseEntity.noContent().build();
    }
}