package com.bookreview.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bookreview.entities.Book;
import com.bookreview.entities.Review;
import com.bookreview.entities.User;
import com.bookreview.repos.BookRepo;
import com.bookreview.repos.ReviewRepo;


@Service
public class BookService {

	@Autowired
	BookRepo bookRepo;
	
	@Autowired
	ReviewRepo reviewRepo;
	
	@Autowired
	UserService userService;
	
	
	
	//AddBook
	public Book createBook(Book book, int userId) {
	    System.out.println("UserId: " + userId);
	    System.out.println("Book Data: " + book);

	    User user = userService.getUser(userId); // Ensure user exists
	    book.setUser(user);
	    book.setLastModifiedDate(LocalDateTime.now()); 
	    return bookRepo.save(book); // Watch console for error
	}

	
	//GetAllBooks
    public List<Book> getAllBooks(String author, String gener) {
        if (author != null && gener != null) {
            return bookRepo.findByAuthorAndGener(author, gener);
        } else if (author != null) {
            return bookRepo.findByAuthor(author);
        } else if (gener != null) {
            return bookRepo.findByGener(gener);
        } else {
            return bookRepo.findAll();
        }
    }


	
	//GetBookById
   
    public Map<String, Object> getBookDetailsById(int bookId, int page, int size) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        // Average rating
        List<Review> allReviews = reviewRepo.findByBookId(bookId);
        double averageRating = allReviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        // Paginated reviews
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> paginatedReviews = reviewRepo.findAllByBookId(bookId, pageable);

        // Response map
        Map<String, Object> response = new HashMap<>();
        response.put("book", book);
        response.put("averageRating", averageRating);
        response.put("reviews", paginatedReviews.getContent());
        response.put("currentPage", paginatedReviews.getNumber());
        response.put("totalPages", paginatedReviews.getTotalPages());
        response.put("totalReviews", paginatedReviews.getTotalElements());

        return response;
    }
    
    
    //delete book
    public void deleteBook(int bookId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        bookRepo.delete(book);
    }
}
