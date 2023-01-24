package com.example.bookapp.service;

import com.example.bookapp.entity.Book;

import java.util.List;

public interface BookService {

    Book saveBook(Long bookshelfId, Book bookRequest) throws Exception;

    List<Book> getAllBooks() throws Exception;

    List<Book> getAllBooksFromBookshelf(Long bookshelfId) throws Exception;

    Book getBookById(Long id) throws Exception;

    Book updateBookById(Long id, Book bookRequest) throws Exception;

    void deleteBookById(Long id) throws Exception;

    void deleteBookFromBookshelf(Long bookshelfId, Long bookId) throws Exception;
}
