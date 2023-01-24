package com.example.bookapp.serviceImpl;

import com.example.bookapp.auth.AuthorizationService;
import com.example.bookapp.entity.Book;
import com.example.bookapp.entity.Bookshelf;
import com.example.bookapp.exception.DuplicateResourceException;
import com.example.bookapp.exception.ResourceNotFoundException;
import com.example.bookapp.repository.BookRepository;
import com.example.bookapp.repository.BookshelfRepository;
import com.example.bookapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private BookshelfRepository bookshelfRepository;

    //    Create / Add Existing Book to Bookshelf
    @Override
    public Book saveBook(Long bookshelfId, Book bookRequest) throws Exception {
        Bookshelf bookshelf = bookshelfRepository.findById(bookshelfId)
                .orElseThrow(() -> new ResourceNotFoundException("bookshelf not found with id: " + bookshelfId));

        if (bookshelf.getBooks().stream().anyMatch(book -> book.getId().equals(bookRequest.getId()))) {
            throw new DuplicateResourceException("Bookshelf already contains this book");
        }

        bookRequest.setUser(bookshelf.getUser());
        Long bookId = bookRequest.getId();

//        book id given
        if (bookId != null) {
//            throws exception if the given book id does not exist
            Book _book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new ResourceNotFoundException("book not found with id: " + bookId));
            bookshelf.addBook(_book);
            bookshelfRepository.save(bookshelf);
            return _book;
        }

//        book id not given, so create book
        bookshelf.addBook(bookRequest);
        return bookRepository.save(bookRequest);
    }

    //    Get all Books
    @Override
    public List<Book> getAllBooks(){
        List<Book> books = bookRepository.findAll();
        return books;
    }

    //    Get all Books of a Bookshelf
    @Override
    public List<Book> getAllBooksFromBookshelf(Long bookshelfId) throws Exception {
        Bookshelf bookshelf = bookshelfRepository.findById(bookshelfId)
                .orElseThrow(() -> new ResourceNotFoundException("bookshelf not found with id: " + bookshelfId));
        List<Book> books = bookRepository.findBooksByBookshelfsId(bookshelfId);
        return books;
    }

    //    Get Book by id
    @Override
    public Book getBookById(Long bookshelfId) throws ResourceNotFoundException {
        Book book = bookRepository.findById(bookshelfId)
                .orElseThrow(() -> new ResourceNotFoundException("book not found with id: " + bookshelfId));
        return book;
    }

    //    Update Book by id
    @Override
    public Book updateBookById(Long bookshelfId, Book bookRequest) throws ResourceNotFoundException {
        Book book = bookRepository.findById(bookshelfId)
                .orElseThrow(() -> new ResourceNotFoundException("book not found with id: " + bookshelfId));
        book.setName(bookRequest.getName());
        book.setRating(bookRequest.getRating());
        book.setReview(bookRequest.getReview());
        return bookRepository.save(book);
    }

    //    Delete Book by id
    @Override
    public void deleteBookById(Long bookshelfId) throws ResourceNotFoundException {
        if (!bookRepository.existsById(bookshelfId)) {
            throw new ResourceNotFoundException("book not found with id: " + bookshelfId);
        }
        bookRepository.deleteById(bookshelfId);
    }

    //    Delete Book from Bookshelf by bookshelfId and bookId
    @Override
    public void deleteBookFromBookshelf(Long bookshelfId, Long bookId) throws Exception {
        Bookshelf bookshelf = bookshelfRepository.findById(bookshelfId)
                .orElseThrow(() -> new ResourceNotFoundException("bookshelf not found with id: " + bookshelfId));
        bookshelf.removeBook(bookId);
        bookshelfRepository.save(bookshelf);
    }
}
