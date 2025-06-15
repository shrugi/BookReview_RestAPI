package com.bookreview.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookreview.dtos.UserRegisterDto;
import com.bookreview.entities.User;
import com.bookreview.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/public")
public class PublicController {

	@Autowired
	UserService userService;
	
	
	@PostMapping("/register")
	public ResponseEntity<User> createUser(@Valid @RequestBody UserRegisterDto userRegisterObj) {
		return new ResponseEntity<>(userService.createUser(userRegisterObj), HttpStatus.CREATED);
	}
	

	
}
