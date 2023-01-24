package com.example.bookapp.auth;

import com.example.bookapp.entity.Book;
import com.example.bookapp.entity.Bookshelf;
import com.example.bookapp.entity.Role;
import com.example.bookapp.entity.User;
import com.example.bookapp.exception.ResourceNotFoundException;
import com.example.bookapp.exception.UnauthorizedAccessException;
import com.example.bookapp.repository.BookRepository;
import com.example.bookapp.repository.BookshelfRepository;
import com.example.bookapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BookshelfRepository bookshelfRepository;

    @Autowired
    private final BookRepository bookRepository;

    public void isUserAuthorized(User userPrincipal, Long userId) throws Exception {
        if (userPrincipal.getRole() == Role.ADMIN) return;

        if (!userPrincipal.getId().equals(userId)) {
            throw new UnauthorizedAccessException("user does not have access to the requested user account");
        }
    }

    public void isUserAdmin(User userPrincipal) throws Exception {
        if (userPrincipal.getRole() != Role.ADMIN) {
            throw new UnauthorizedAccessException("user is not an admin");
        }
    }

    public void isUserAuthorizedForBookshelf(User userPrincipal, Long bookshelfId) throws Exception {
        if (userPrincipal.getRole() == Role.ADMIN) return;

        Bookshelf bookshelf = bookshelfRepository.findById(bookshelfId)
                .orElseThrow(() -> new ResourceNotFoundException("bookshelf id not found"));

        if (!bookshelf.getUser().getId().equals(userPrincipal.getId())) {
            throw new UnauthorizedAccessException("user does not have access to the requested bookshelf");
        }
    }

    public void isUserAuthorizedForBook(User userPrincipal, Long bookId) throws Exception {
        if (userPrincipal.getRole() == Role.ADMIN) return;

//        User is making a new book
        if (bookId == null) return;

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("book id not found"));

        if (!book.getUser().getId().equals(userPrincipal.getId())) {
            throw new UnauthorizedAccessException("user does not have access to the requested book");
        }
    }
}
