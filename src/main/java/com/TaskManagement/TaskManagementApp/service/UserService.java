package com.TaskManagement.TaskManagementApp.service;

import com.TaskManagement.TaskManagementApp.dto.CreateUserDTO;
import com.TaskManagement.TaskManagementApp.entity.User;
import com.TaskManagement.TaskManagementApp.exception.EmailAlreadyRegisteredException;
import com.TaskManagement.TaskManagementApp.exception.ExceptionDetails;
import com.TaskManagement.TaskManagementApp.repository.UserRepository;
import com.TaskManagement.TaskManagementApp.utils.IdGeneratorService;
import com.TaskManagement.TaskManagementApp.utils.IdTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final IdGeneratorService idGeneratorService;

    @Autowired
    public UserService(UserRepository userRepository, IdGeneratorService idGeneratorService) {
        this.userRepository = userRepository;
        this.idGeneratorService = idGeneratorService;
    }

    public ResponseEntity<Object> createANewUser(CreateUserDTO user) throws EmailAlreadyRegisteredException {
        Optional<User> existingEmail = this.userRepository.findByEmail(user.getEmail());

        if(existingEmail.isPresent()) {
            throw new EmailAlreadyRegisteredException("An attempt has been made to register an email which already exists in the database",
                    new ExceptionDetails(HttpStatus.BAD_REQUEST.value(), "The entered email address is already registered"));
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