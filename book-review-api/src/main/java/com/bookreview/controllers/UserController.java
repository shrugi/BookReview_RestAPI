package com.bookreview.controllers;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookreview.dtos.UserRegisterDto;
import com.bookreview.entities.User;
import com.bookreview.services.UserService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/self")
	public ResponseEntity<User> getUserById() {
		User user = userService.getCurrentUser();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping("/self")
	public ResponseEntity<User> updateUserById(@Valid @RequestBody User user) {
		User curuser = userService.getCurrentUser();
		return new ResponseEntity<>(userService.updateUser(curuser.getId(), user), HttpStatus.OK);
	}


}
