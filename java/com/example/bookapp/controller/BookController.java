package com.example.bookapp.controller;

import com.example.bookapp.auth.AuthorizationService;
import com.example.bookapp.entity.Book;
import com.example.bookapp.entity.User;
import com.example.bookapp.repository.BookshelfRepository;
import com.example.bookapp.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookshelfRepository bookshelfRepository;

    @Autowired
    private AuthorizationService authorizationService;

    //    Create / Add Existing Book to Bookshelf
    @PostMapping("/bookshelfs/{bookshelfId}/books")
    private ResponseEntity<Book> saveBook(
            @AuthenticationPrincipal User userPrincipal,
            @PathVariable Long bookshelfId,
            @RequestBody @Valid Book bookRequest
    ) throws Exception {
        authorizationService.isUserAuthorizedForBookshelf(userPrincipal, bookshelfId);
        authorizationService.isUserAuthorizedForBook(userPrincipal, bookRequest.getId());
        Book savedBook = bookService.saveBook(bookshelfId, bookRequest);
        return new ResponseEntity<>(savedBook, HttpStatus.OK);
    }

//    Get all Books
@GetMapping("/books")
private ResponseEntity<List<Book>> getAllBooks(
        @AuthenticationPrincipal User userPrincipal
        ) throws Exception {
        authorizationService.isUserAdmin(userPrincipal);
        List<Book> books = bookService.getAllBooks();
    if (books.isEmpty()) {
        return new ResponseEntity<>(books, HttpStatus.NO_CONTENT);
    } else {
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}

        //    Get all Books of a Bookshelf
    @GetMapping("/bookshelfs/{bookshelfId}/books")
    private ResponseEntity<List<Book>> getAllBooksFromBookshelf(
            @AuthenticationPrincipal User userPrincipal,
            @PathVariable Long bookshelfId
    ) throws Exception {
        authorizationService.isUserAuthorizedForBookshelf(userPrincipal, bookshelfId);
        List<Book> books = bookService.getAllBooksFromBookshelf(bookshelfId);
        if (books.isEmpty()) {
            return new ResponseEntity<>(books, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(books, HttpStatus.OK);
        }
    }

    //    Get Book by id
    @GetMapping("books/{bookId}")
    private ResponseEntity<Book> getBookById(
            @AuthenticationPrincipal User userPrincipal,
            @PathVariable Long bookId
    ) throws Exception {
        authorizationService.isUserAuthorizedForBook(userPrincipal, bookId);
        Book book = bookService.getBookById(bookId);
        return new ResponseEntity<>(book, HttpStatus.FOUND);
    }

    //    Update Book by id
    @PutMapping("books/{bookId}")
    private ResponseEntity<Book> updateBookById(
            @AuthenticationPrincipal User userPrincipal,
            @PathVariable Long bookId,
            @RequestBody @Valid Book bookRequest
    ) throws Exception {
        authorizationService.isUserAuthorizedForBook(userPrincipal, bookId);
        Book book = bookService.updateBookById(bookId, bookRequest);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    //   Delete Book by id
    @DeleteMapping("books/{bookId}")
    private ResponseEntity<Book> deleteBookById(
            @AuthenticationPrincipal User userPrincipal,
            @PathVariable Long bookId
    ) throws Exception {
        authorizationService.isUserAuthorizedForBook(userPrincipal, bookId);
        bookService.deleteBookById(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //    Delete Book from Bookshelf by bookshelfId and bookId
    @DeleteMapping("/bookshelfs/{bookshelfId}/books/{bookId}")
    private ResponseEntity<Book> deleteBookFromBookshelf(
            @AuthenticationPrincipal User userPrincipal,
            @PathVariable Long bookshelfId,
            @PathVariable Long bookId
    ) throws Exception {
        authorizationService.isUserAuthorizedForBookshelf(userPrincipal, bookshelfId);
        bookService.deleteBookFromBookshelf(bookshelfId, bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
