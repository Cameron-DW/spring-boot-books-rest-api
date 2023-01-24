package com.example.bookapp.repository;

import com.example.bookapp.entity.Bookshelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookshelfRepository extends JpaRepository<Bookshelf, Long> {
    List<Bookshelf> findBookshelfsByUserId(Long userId);
}
