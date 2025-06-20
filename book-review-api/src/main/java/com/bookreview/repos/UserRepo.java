package com.bookreview.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookreview.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> { 
	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);
}   
   