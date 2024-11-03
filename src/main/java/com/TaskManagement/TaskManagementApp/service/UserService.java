package com.TaskManagement.TaskManagementApp.service;

import com.TaskManagement.TaskManagementApp.dto.CreateUserDTO;
import com.TaskManagement.TaskManagementApp.dto.UpdateUserDTO;
import com.TaskManagement.TaskManagementApp.entity.User;
import com.TaskManagement.TaskManagementApp.exception.EmailAlreadyRegisteredException;
import com.TaskManagement.TaskManagementApp.exception.ExceptionDetails;
import com.TaskManagement.TaskManagementApp.exception.UserNotFoundException;
import com.TaskManagement.TaskManagementApp.repository.UserRepository;
import com.TaskManagement.TaskManagementApp.utils.IdGeneratorService;
import com.TaskManagement.TaskManagementApp.utils.IdTypes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public User createANewUser(CreateUserDTO user) throws EmailAlreadyRegisteredException {
        Optional<User> existingEmail = this.userRepository.findByEmail(user.getEmail());

        if(existingEmail.isPresent()) {
            throw new EmailAlreadyRegisteredException("An attempt has been made to register an email which already exists in the database",
                    new ExceptionDetails(HttpStatus.BAD_REQUEST.value(), "The entered email address is already registered"));
        }

        User newUser = new User.builder()
                .id(idGeneratorService.generateId(IdTypes.USER))
                .name(user.getName())
                .lastName(user.getLast_name())
                .email(user.getEmail())
                .build();

        return this.userRepository.save(newUser);
    }

    public Page<User> retrieveAllUsers(int page, int perPage) {
        PageRequest paging = PageRequest.of(page -1, perPage);

        return this.userRepository.findAll(paging);
    }

    public User retrieveASingleUser(long id) throws UserNotFoundException {
        return this.userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("An attempt was made to search for a user by an id that is not registered in the database",
                        new ExceptionDetails(HttpStatus.NOT_FOUND.value(), "The user you are trying to find does not exist"))
        );
    }

    public User updateUser(long userId, UpdateUserDTO user) throws UserNotFoundException {
        User existingUser = this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("An attempt was made to search for a user by an id that is not registered in the database",
                        new ExceptionDetails(HttpStatus.NOT_FOUND.value(), "The user you are trying to update does not exist"))
        );

        return this.userRepository.save(update(user, existingUser));
    }

    public void deleteUser(long userId) throws UserNotFoundException {
        this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("An attempt was made to search for a user by an id that is not registered in the database",
                            new ExceptionDetails(HttpStatus.NOT_FOUND.value(), "The user you are trying to delete does not exist"))
        );

        this.userRepository.deleteById(userId);
    }

    private User update(UpdateUserDTO source, User target) {
        if(source.getName() != null) {
            target.setName(source.getName());
        }

        if(source.getLast_name() != null) {
            target.setLastName(source.getLast_name());
        }

        if(source.getEmail() != null) {
            target.setEmail(source.getEmail());
        }

        return target;
    }
}