package com.example.bookapp.controller;

import com.example.bookapp.auth.AuthorizationService;
import com.example.bookapp.entity.Bookshelf;
import com.example.bookapp.entity.User;
import com.example.bookapp.repository.BookshelfRepository;
import com.example.bookapp.service.BookshelfService;
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
public class BookshelfController {

    @Autowired
    private BookshelfService bookshelfService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private BookshelfRepository bookshelfRepository;


    //    Create a new Bookshelf for a User
    @PostMapping("/users/{userId}/bookshelfs")
    public ResponseEntity<Bookshelf> saveBookshelf(
            @AuthenticationPrincipal User userPrincipal,
            @PathVariable Long userId,
            @RequestBody @Valid Bookshelf bookshelfRequest
    ) throws Exception {
        authorizationService.isUserAuthorized(userPrincipal, userId);
        Bookshelf savedBookshelf = bookshelfService.saveBookshelf(userId, bookshelfRequest);
        return new ResponseEntity<>(bookshelfRequest, HttpStatus.CREATED);
    }

    // Get all Bookshelfs
    @GetMapping("/bookshelfs")
    public ResponseEntity<List<Bookshelf>> getAllBookshelfs(
            @AuthenticationPrincipal User userPrincipal
    ) throws Exception {
        authorizationService.isUserAdmin(userPrincipal);
        List<Bookshelf> bookshelfs = bookshelfService.getAllBookshelfs();
       if (bookshelfs.isEmpty()) {
            return new ResponseEntity<>(bookshelfs, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bookshelfs, HttpStatus.OK);
        }
    }

    //    Get all Bookshelfs of a User
    @GetMapping("/users/{userId}/bookshelfs")
    public ResponseEntity<List<Bookshelf>> getAllBookshelfsFromUser(
            @AuthenticationPrincipal User userPrincipal,
            @PathVariable Long userId
    ) throws Exception {
        authorizationService.isUserAuthorized(userPrincipal, userId);
        List<Bookshelf> bookshelfs = bookshelfService.getAllBookshelfsFromUser(userId);
        if (bookshelfs.isEmpty()) {
            return new ResponseEntity<>(bookshelfs, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bookshelfs, HttpStatus.OK);
        }
    }
//    Get Bookshelf By id
    @GetMapping("bookshelfs/{bookshelfId}")
    public ResponseEntity<Bookshelf> getBookshelfById(
            @AuthenticationPrincipal User userPrincipal,
            @PathVariable Long bookshelfId
    ) throws Exception {
        authorizationService.isUserAuthorizedForBookshelf(userPrincipal, bookshelfId);
        Bookshelf returnedBookshelf = bookshelfService.getBookshelfById(bookshelfId);
        return new ResponseEntity<>(returnedBookshelf, HttpStatus.OK);
    }

    //    Update a Bookshelf by id
    @PutMapping("/bookshelfs/{bookshelfId}")
    public ResponseEntity<Bookshelf> updateBookshelfById(
            @AuthenticationPrincipal User userPrincipal,
            @PathVariable Long bookshelfId,
            @RequestBody @Valid Bookshelf bookshelfRequest
    ) throws Exception {
        authorizationService.isUserAuthorizedForBookshelf(userPrincipal, bookshelfId);
        Bookshelf returnedBookshelf = bookshelfService.updateBookshelfById(bookshelfId, bookshelfRequest);
        return new ResponseEntity<>(returnedBookshelf, HttpStatus.OK);
    }

    //    Delete a Bookshelf by id
    @DeleteMapping("bookshelfs/{bookshelfId}")
    public ResponseEntity<Bookshelf> deleteBookshelfById(
            @AuthenticationPrincipal User userPrincipal,
            @PathVariable Long bookshelfId
    ) throws Exception {
        authorizationService.isUserAuthorizedForBookshelf(userPrincipal, bookshelfId);
        bookshelfService.deleteBookshelfById(bookshelfId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
