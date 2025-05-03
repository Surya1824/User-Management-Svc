package com.surya.user.management.svc.controller;

import com.surya.user.management.svc.enums.Role;
import com.surya.user.management.svc.model.LoginRequest;
import com.surya.user.management.svc.model.LoginResponse;
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
public class CustomerController {
	
	private final UserService userservice;

	public CustomerController(UserService userService) {
		this.userservice = userService;
	}

	@GetMapping
	public String greetCustomer() {
		return "Hello, How is your Day?";
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserDetails userdetails) throws UserAlreadyExistException{
        return userservice.registerUser(userdetails, Role.CUSTOMER);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginUser(@RequestBody @Valid LoginRequest request){
		return userservice.verifyUser(request, Role.CUSTOMER);
	}

}
