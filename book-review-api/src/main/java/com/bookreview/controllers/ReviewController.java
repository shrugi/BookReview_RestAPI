package com.bookreview.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookreview.entities.Review;
import com.bookreview.services.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/{userId}/{bookId}")
    public Review addReview(@PathVariable int userId, @PathVariable int bookId,
                            @RequestBody Review review) {
        return reviewService.addReview(userId, bookId, review);
    }

    @GetMapping("/book/{bookId}")
    public List<Review> getReviewsForBook(@PathVariable int bookId) {
        return reviewService.getReviewsForBook(bookId);
    }
    
    
   
    @PutMapping("/{reviewId}")
    public Review updateReview(@PathVariable int reviewId,
                               @RequestBody Review updatedReview,
                               Principal principal) {
        return reviewService.updateReview(reviewId, updatedReview, principal.getName());
    }


}
