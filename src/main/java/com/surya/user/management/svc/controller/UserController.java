package com.surya.user.management.svc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surya.user.management.svc.exceptions.UserAlreadyExistException;
import com.surya.user.management.svc.model.UserDetails;
import com.surya.user.management.svc.model.UserResponse;
import com.surya.user.management.svc.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class UserController {
	
	private final UserService userservice;

	public UserController(UserService userService) {
		this.userservice = userService;
	}

	@GetMapping
	public String greetCustomer() {
		return "Hello, How is your Day?";
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserDetails userdetails) throws UserAlreadyExistException{
		ResponseEntity<UserResponse> response = userservice.registerUser(userdetails);
		return response;
	}

}
