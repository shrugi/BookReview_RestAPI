package com.bookreview.services;

import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bookreview.dtos.UserRegisterDto;
import com.bookreview.entities.Role;
import com.bookreview.entities.User;
import com.bookreview.repos.RoleRepo;
import com.bookreview.repos.UserRepo;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RoleRepo roleRepo;

@Autowired
AuthenticationManager authenticationManager;



	@Transactional
	public User createUser(UserRegisterDto userRegisterObj) {
		Optional<User> existingUser = userRepo.findByEmail(userRegisterObj.getEmail());
		if (existingUser.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User user already exist");
		}
		if (userRegisterObj.getRoles() == null || userRegisterObj.getRoles().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User must have atleat one role");
		}
		Set<Role> managedRoles = getManagedRoles(userRegisterObj.getRoles());
		User user = new User();
		user.setUsername(userRegisterObj.getUsername());
		user.setEmail(userRegisterObj.getEmail());
		user.setPassword(passwordEncoder.encode(userRegisterObj.getPassword()));
		user.setRoles(managedRoles);
		return this.userRepo.save(user);
	}

	// helper method to fetch and managed roles from the database

	private Set<Role> getManagedRoles(Set<Role> roles) {
		Set<Role> managedRoles = new HashSet<>();
		for (Role role : roles) {
			Role managedRole = roleRepo.findByName(role.getName()).orElseThrow(
					() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role '" + role.getName() + "' not found"));
			managedRoles.add(managedRole);

		}
		return managedRoles;
	}


	public Page<User> getUsers(Pageable pageable) {
		return this.userRepo.findAll(pageable);
	}

	public User getUser(int id) {
		return this.userRepo.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + id + " not found"));
	}

	public User updateUser(int id, User userInput) {
	    User existingUser = userRepo.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + id + " not found"));

	    // Update only allowed fields
	    existingUser.setUsername(userInput.getUsername());
	    existingUser.setEmail(userInput.getEmail());

	    // Password and roles should NOT be updated
	    // Password stays encrypted as-is, no changes here

	    return userRepo.save(existingUser);
	}
	
	
	public void deleteUser(int id) {
		getUser(id);
		this.userRepo.deleteById(id);
	}

	public User getUserByEmail(String email) {
		return this.userRepo.findByEmail(email).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + email + " not found"));

	}

	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal=authentication.getPrincipal()	;
		String email=((UserDetails)principal).getUsername()	;
		User user=getUserByEmail(email);
		return user;
	}
	
	
	
	public void deleteUserById(int id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        userRepo.delete(user);
    }


}