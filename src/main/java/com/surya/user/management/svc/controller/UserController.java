package com.surya.user.management.svc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surya.user.management.svc.model.UserDetails;
import com.surya.user.management.svc.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class UserController {
	
	@Autowired
	UserService userservice;
	
	@GetMapping
	public String greetCustomer() {
		return "Hello, How is your Day?";
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody @Valid UserDetails userdetails){
		String status = userservice.registerUser(userdetails);
		return ResponseEntity.status(HttpStatus.CREATED).body(status);
	}

}
