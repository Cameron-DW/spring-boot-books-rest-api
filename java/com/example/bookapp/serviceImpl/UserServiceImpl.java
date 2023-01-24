package com.example.bookapp.serviceImpl;

import com.example.bookapp.auth.AuthorizationService;
import com.example.bookapp.entity.User;
import com.example.bookapp.exception.ResourceNotFoundException;
import com.example.bookapp.repository.BookshelfRepository;
import com.example.bookapp.repository.UserRepository;
import com.example.bookapp.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookshelfRepository bookshelfRepository;

    @Autowired
    private AuthorizationService authorizationService;

    @Override
    public List<User> getAllUsers() throws Exception {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.setBookshelfs(bookshelfRepository.findBookshelfsByUserId(user.getId()));
        }
        return users;
    }

    @Override
    public User getUserById(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + userId));
        user.setBookshelfs(bookshelfRepository.findBookshelfsByUserId(userId));
        return user;
    }

    @Transactional
    @Override
    public User updateUserById(Long userId, User userRequest) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + userId));
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUserById(Long userId) throws ResourceNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("user not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }
}
