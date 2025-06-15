package com.bookreview.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookreview.entities.Review;
@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {
	 List<Review> findByBookId(int bookId); // for average
	 Page<Review> findAllByBookId(int bookId, org.springframework.data.domain.Pageable pageable); // ✅ RIGHT
	 boolean existsByUserIdAndBookId(int userId, int bookId);
	 Optional<Review> findByUserIdAndBookId(int userId, int bookId);
	Optional<Review> findByBookIdAndUserId(int bookId, int id);

}
