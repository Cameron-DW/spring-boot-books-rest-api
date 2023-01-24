package com.example.bookapp.service;

import com.example.bookapp.entity.Bookshelf;

import java.util.List;

public interface BookshelfService {

    Bookshelf saveBookshelf(Long userId, Bookshelf bookshelfRequest) throws Exception;

    List<Bookshelf> getAllBookshelfs();

    List<Bookshelf> getAllBookshelfsFromUser(Long userId);

    Bookshelf getBookshelfById(Long bookshelfId) throws Exception;

    Bookshelf updateBookshelfById(Long bookshelfId, Bookshelf bookshelfRequest) throws Exception;

    void deleteBookshelfById(Long bookshelfId) throws Exception;
}
