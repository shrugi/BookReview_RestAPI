package com.bookreview.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookreview.services.BookService;
import com.bookreview.services.ReviewService;
import com.bookreview.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserService userService;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	ReviewService reviewService;
	
	 @DeleteMapping("/user/{userId}/book/{bookId}")
	    public String deleteReviewByUserAndBook(@PathVariable int userId, @PathVariable int bookId) {
	        reviewService.deleteReviewByUserAndBook(userId, bookId);
	        return "Review deleted successfully";
	    }
	 
	  
	    @DeleteMapping("users/{id}")
	    public ResponseEntity<String> deleteUser(@PathVariable int id) {
	        userService.deleteUserById(id);
	        return ResponseEntity.ok("User deleted successfully.");
	    }
	    
	
	    @DeleteMapping("books/{id}")
	    public ResponseEntity<?> deleteBook(@PathVariable int id) {
	        bookService.deleteBook(id);
	        return ResponseEntity.ok("Book deleted successfully");
	    }

}
