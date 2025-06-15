package com.bookreview.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookreview.entities.Book;


@Repository
public interface BookRepo  extends JpaRepository<Book, Integer>  {
    List<Book> findByAuthor(String author);
    List<Book> findByGener(String gener);
    List<Book> findByAuthorAndGener(String author, String gener);
}
