package com.bookreview.dtos;

import java.util.HashSet;
import java.util.Set;

import com.bookreview.entities.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
	private String username;

	private String email;

	@NotBlank
	@Size(min = 6, max = 10, message = "Password should be between 6-10 characters")
	@Pattern(regexp = "^[A-Za-z0-9]+$",message = "Only a Combination of numbers and characters")
	private String password;

	private Set<Role> roles = new HashSet<>();

}
