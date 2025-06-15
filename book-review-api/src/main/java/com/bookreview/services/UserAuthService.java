package com.bookreview.services;

import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bookreview.entities.User;
import com.bookreview.repos.UserRepo;




@Service
public class UserAuthService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(email).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
						"User  with emial " + email + " not found"));

		return new org.springframework.security.core.userdetails.User(
				user.getEmail(), 
				user.getPassword(),
				user.getRoles()
				.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
						.collect(Collectors.toList()));
	}
}
