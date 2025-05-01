package com.surya.user.management.svc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.surya.user.management.svc.enums.Role;
import com.surya.user.management.svc.exceptions.UserAlreadyExistException;
import com.surya.user.management.svc.model.UserDetails;
import com.surya.user.management.svc.model.UserResponse;
import com.surya.user.management.svc.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserService {
	
	private final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private final UserRepository userRepo;
	
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {	
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	public ResponseEntity<UserResponse> registerUser(@Valid UserDetails userdetails) throws UserAlreadyExistException {

		if (userRepo.findByEmailId(userdetails.getEmailId()).isPresent()) {
			throw new UserAlreadyExistException("User Already Registered");
		} else {
			userdetails.setRole(Role.CUSTOMER);// default role
			userdetails.setPassword(passwordEncoder.encode(userdetails.getPassword()));
			UserDetails saveDetails = userRepo.save(userdetails);
			logger.debug("Saved user Details: {}", saveDetails);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new UserResponse("UserRegister Successfully", true, saveDetails.getUserId()));
		}

	}

}
