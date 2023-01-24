package com.example.bookapp.controller;

import com.example.bookapp.auth.AuthorizationService;
import com.example.bookapp.entity.User;
import com.example.bookapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;

    //   Get all Users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(
            @AuthenticationPrincipal User userPrincipal
    ) throws Exception {
        authorizationService.isUserAdmin(userPrincipal);
        List<User> allUsers = userService.getAllUsers();
         if (allUsers.isEmpty()) {
            return new ResponseEntity<>(allUsers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
    }

    //    Get a User by the id
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(
            @AuthenticationPrincipal User userPrincipal,
            @PathVariable Long userId
    ) throws Exception {
        authorizationService.isUserAuthorized(userPrincipal, userId);
        User returnedUser = userService.getUserById(userId);
        return new ResponseEntity<>(returnedUser, HttpStatus.OK);
    }

    //    Update a User by the id
    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUserById(
            @AuthenticationPrincipal User userPrincipal,
            @PathVariable Long userId,
            @RequestBody @Valid User userRequest
    ) throws Exception {
        authorizationService.isUserAuthorized(userPrincipal, userId);
        User updatedUser = userService.updateUserById(userId, userRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //    Delete a User by id
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<User> deleteUserById(
            @AuthenticationPrincipal User userPrincipal,
            @PathVariable Long userId
    ) throws Exception {
        authorizationService.isUserAuthorized(userPrincipal, userId);
        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
