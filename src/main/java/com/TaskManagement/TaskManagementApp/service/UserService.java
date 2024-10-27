package com.TaskManagement.TaskManagementApp.service;

import com.TaskManagement.TaskManagementApp.dto.CreateUserDTO;
import com.TaskManagement.TaskManagementApp.entity.User;
import com.TaskManagement.TaskManagementApp.repository.UserRepository;
import com.TaskManagement.TaskManagementApp.utils.IdGeneratorService;
import com.TaskManagement.TaskManagementApp.utils.IdTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private IdGeneratorService idGeneratorService;

    @Autowired
    public UserService(UserRepository userRepository, IdGeneratorService idGeneratorService) {
        this.userRepository = userRepository;
        this.idGeneratorService = idGeneratorService;
    }

    public ResponseEntity<Object> createANewUser(CreateUserDTO user) {
        Optional<User> existingEmail = this.userRepository.findByEmail(user.getEmail());
        HashMap<String, Object> response = new HashMap<>();

        if(existingEmail.isPresent()) {
            response.put("error", "The entered email address is already registered");

            return ResponseEntity.badRequest().body(response);
        }

        User newUser = new User.builder()
                .id(idGeneratorService.generateId(IdTypes.USER))
                .name(user.getName())
                .last_name(user.getLast_name())
                .email(user.getEmail())
                .build();

        return ResponseEntity.ok(this.userRepository.save(newUser));
    }

    public ResponseEntity<Object> retrieveAllUsers(int page, int perPage) {
        PageRequest paging = PageRequest.of(page -1, perPage);

        return ResponseEntity.ok(this.userRepository.findAll(paging));
    }
}