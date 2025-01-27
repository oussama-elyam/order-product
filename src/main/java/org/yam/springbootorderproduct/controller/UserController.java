package org.yam.springbootorderproduct.controller;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.yam.springbootorderproduct.dtoRequest.UserDtoRequest;
import org.yam.springbootorderproduct.model.User;
import org.yam.springbootorderproduct.service.UserService;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createUser(@RequestBody UserDtoRequest body, BindingResult result) {

        try {
            User newUser = new User();

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(body, newUser);

            User newUserCreated = userService.createUser(newUser);

            return new ResponseEntity<>(newUserCreated, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the User: " + e.getMessage());
        }

    }

    @GetMapping("/getPaggination")
    public ResponseEntity<List<User>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<User> Users = userService.getUsers(page, size);

        return new ResponseEntity<>(Users.getContent(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateUserSql(@RequestBody UserDtoRequest body, @PathVariable("id") Long id) {

        User existingUser = new User();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(body, existingUser);

        existingUser.setId(id); // Ensure the id remains unchanged

        User updatedUser = userService.updateUser(existingUser, id);

        if (updatedUser == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}
