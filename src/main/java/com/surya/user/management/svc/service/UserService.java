package com.surya.user.management.svc.service;

import com.surya.user.management.svc.model.LoginRequest;
import com.surya.user.management.svc.model.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

	private final AuthenticationManager authenticationManager;

	public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

	public ResponseEntity<UserResponse> registerUser(@Valid UserDetails userdetails) throws UserAlreadyExistException {

		if (userRepo.findByEmailId(userdetails.getEmailId()).isPresent()) {
			throw new UserAlreadyExistException("User Already Registered");
		} else {
			userdetails.setRole(Role.CUSTOMER);// default role
			userdetails.setPassword(passwordEncoder.encode(userdetails.getPassword()));
			UserDetails saveDetails = userRepo.save(userdetails);
			logger.info("Saved user Details: {}", saveDetails);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new UserResponse("UserRegister Successfully", true, saveDetails.getUserId()));
		}

	}

	public ResponseEntity<LoginResponse> verifyUser(@Valid LoginRequest request) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmailId(), request.getPassword()));
		if(authentication.isAuthenticated()){
			return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse("1w1sq5s1q1sq111a1151.151wdw4Dw4Ds444aX.1xa4d5a5d4a4da51dsaf8fer", true));
		}
		logger.error("Bad Credentials");
		throw new BadCredentialsException("Bad Credentials");

	}

}
