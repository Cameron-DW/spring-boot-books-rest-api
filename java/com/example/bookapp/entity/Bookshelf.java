package com.example.bookapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookshelf_tbl")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bookshelf {

    @Id
    @SequenceGenerator(
            name = "bookshelf_sequence",
            sequenceName = "bookshelf_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bookshelf_sequence"
    )
    private Long id;

    @NotBlank(message = "name should not be blank")
    private String name;

    @ManyToOne(
            optional = false
    )
    @JoinColumn(
            name = "user_Id",
            referencedColumnName = "id"
    )
    @JsonBackReference
    private User user;

    private String description;

    @ManyToMany(
            mappedBy = "bookshelfs",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        this.books.add(book);
        book.getBookshelfs().add(this);
    }

    public void removeBook(long bookId) {
        Book book = this.books.stream().filter(b -> b.getId() == bookId).findFirst().orElse(null);
        if (book != null) {
            this.books.remove(book);
            book.getBookshelfs().remove(this);
        }
    }
}
