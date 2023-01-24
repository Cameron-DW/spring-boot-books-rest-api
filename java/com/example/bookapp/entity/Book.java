package com.example.bookapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book_tbl")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Book {

    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    private Long id;

    private String name;

    private Integer rating;

    private String review;

    @ManyToMany
            (cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "bookshelf_book_map",
            joinColumns = @JoinColumn(
                    name = "book_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "bookshelf_id",
                    referencedColumnName = "id"
            )
    )
    @JsonIgnore
    private List<Bookshelf> bookshelfs = new ArrayList<>();

    @ManyToOne(
            optional = false
    )
    @JoinColumn(
            name = "user_Id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private User user;
}
