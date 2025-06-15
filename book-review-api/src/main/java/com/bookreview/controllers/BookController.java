package com.bookreview.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookreview.entities.Book;
import com.bookreview.services.BookService;
import com.bookreview.services.UserService;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<Book> createBook(@PathVariable int userId, @RequestBody Book book) {
        Book createdBook = bookService.createBook(book, userId);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    
    
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "gener", required = false) String gener) {

        List<Book> books = bookService.getAllBooks(author, gener);
        return ResponseEntity.ok(books);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookDetails(
            @PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Map<String, Object> response = bookService.getBookDetailsById(id, page, size);
        return ResponseEntity.ok(response);
    }
    
    
    
   

}

