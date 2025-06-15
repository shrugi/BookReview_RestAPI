package com.bookreview.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bookreview.entities.Book;
import com.bookreview.entities.Review;
import com.bookreview.entities.User;
import com.bookreview.repos.BookRepo;
import com.bookreview.repos.ReviewRepo;
import com.bookreview.repos.UserRepo;

@Service
public class ReviewService {
	   @Autowired
	    private ReviewRepo reviewRepo;

	    @Autowired
	    private BookRepo bookRepo;

	    @Autowired
	    private UserRepo userRepo;
	
	    public Review addReview(int userId, int bookId, Review review) {
	        if (reviewRepo.existsByUserIdAndBookId(userId, bookId)) {
	            throw new ResponseStatusException(HttpStatus.CONFLICT, "User has already reviewed this book");
	        }

	        User user = userRepo.findById(userId).orElseThrow(() ->
	            new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	        Book book = bookRepo.findById(bookId).orElseThrow(() ->
	            new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

	        review.setUser(user);
	        review.setBook(book);
	        return reviewRepo.save(review);
	    }

	    public List<Review> getReviewsForBook(int bookId) {
	        return reviewRepo.findByBookId(bookId);
	    }
	    
	    public void deleteReviewByUserAndBook(int userId, int bookId) {
	        Review review = reviewRepo.findByUserIdAndBookId(userId, bookId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
	        reviewRepo.delete(review);
	    }
	    
	    
	  
	    public Review updateReview(int reviewId, Review updatedReview, String username) {
	        Review existingReview = reviewRepo.findById(reviewId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));

	        // Check if the logged-in user owns this review
	        if (!existingReview.getUser().getEmail().equals(username)) {
	            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to update this review");
	        }

	        // Update allowed fields (not user or book)
	        existingReview.setRating(updatedReview.getRating());
	        existingReview.setComment(updatedReview.getComment());

	        return reviewRepo.save(existingReview);
	    }



}
