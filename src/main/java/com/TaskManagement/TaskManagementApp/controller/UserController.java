package com.TaskManagement.TaskManagementApp.controller;

import com.TaskManagement.TaskManagementApp.dto.CreateUserDTO;
import com.TaskManagement.TaskManagementApp.entity.User;
import com.TaskManagement.TaskManagementApp.exception.EmailAlreadyRegisteredException;
import com.TaskManagement.TaskManagementApp.exception.UserNotFoundException;
import com.TaskManagement.TaskManagementApp.http.Paging;
import com.TaskManagement.TaskManagementApp.http.PagingResponse;
import com.TaskManagement.TaskManagementApp.service.UserService;
import com.TaskManagement.TaskManagementApp.utils.ErrorResponseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> createANewUser(@RequestBody @Valid CreateUserDTO user, BindingResult result) throws EmailAlreadyRegisteredException {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorResponseService.GenerateErrorResponse(result));
        }

        return ResponseEntity.ok(this.userService.createANewUser(user));
    }

    @GetMapping
    public ResponseEntity<Object> retrieveAllUsers(@RequestParam(name = "page", defaultValue = "1") String page, @RequestParam(name = "per_page", defaultValue = "50") String perPage) {
        int finalPage = Integer.parseInt(page);
        int finalPerPage = Integer.parseInt(perPage);

        if(finalPage < 1 || finalPerPage < 1 || finalPerPage > 50) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "The page cannot be 0 and the number of records per page cannot be less than 1 or more than 50.");

            return ResponseEntity.badRequest().body(errorResponse);
        }

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
    public ResponseEntity<Object> retrieveASingleUser(@PathVariable("userId") String userId) throws UserNotFoundException {
        return ResponseEntity.ok(this.userService.retrieveASingleUser(Long.parseLong(userId)));
    }
}