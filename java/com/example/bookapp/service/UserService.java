package com.example.bookapp.service;

import com.example.bookapp.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers() throws Exception;

    User getUserById(Long userId) throws Exception;

    User updateUserById(Long userId, User userRequest) throws Exception;

    void deleteUserById(Long userId) throws Exception;
}
