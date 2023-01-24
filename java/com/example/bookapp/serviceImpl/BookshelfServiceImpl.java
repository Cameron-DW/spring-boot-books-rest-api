package com.example.bookapp.serviceImpl;

import com.example.bookapp.auth.AuthorizationService;
import com.example.bookapp.entity.Bookshelf;
import com.example.bookapp.entity.User;
import com.example.bookapp.exception.ResourceNotFoundException;
import com.example.bookapp.repository.BookshelfRepository;
import com.example.bookapp.repository.UserRepository;
import com.example.bookapp.service.BookshelfService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookshelfServiceImpl implements BookshelfService {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookshelfRepository bookshelfRepository;

    @Override
    public Bookshelf saveBookshelf(Long userId, Bookshelf bookshelfRequest) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + userId));
        bookshelfRequest.setUser(user);
        bookshelfRepository.save(bookshelfRequest);
        return bookshelfRequest;
    }

    @Override
    public List<Bookshelf> getAllBookshelfs(){
        return bookshelfRepository.findAll();
    }

    @Override
    public List<Bookshelf> getAllBookshelfsFromUser(Long userId) {
        return bookshelfRepository.findBookshelfsByUserId(userId);
    }

    @Override
    public Bookshelf getBookshelfById(Long bookshelfId) throws Exception {
        Bookshelf bookshelf = bookshelfRepository.findById(bookshelfId)
                .orElseThrow(() -> new ResourceNotFoundException("bookshelf not found with id: " + bookshelfId));
        return bookshelf;
    }

    //    Update Bookshelf
    @Transactional
    @Override
    public Bookshelf updateBookshelfById(Long bookshelfId, Bookshelf bookshelfRequest) throws Exception {
        Bookshelf bookshelf = bookshelfRepository.findById(bookshelfId)
                .orElseThrow(() -> new ResourceNotFoundException("bookshelf not found with id: " + bookshelfId));
        bookshelf.setName(bookshelfRequest.getName());
        bookshelf.setDescription(bookshelfRequest.getDescription());
        return bookshelfRepository.save(bookshelf);
    }

    @Override
    @Transactional
    public void deleteBookshelfById(Long bookshelfId) throws Exception {
        Bookshelf bookshelf = bookshelfRepository.findById(bookshelfId)
                .orElseThrow(() -> new ResourceNotFoundException("bookshelf not found with id: " + bookshelfId));
        bookshelfRepository.deleteById(bookshelfId);
    }
}
