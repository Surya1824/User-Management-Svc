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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.surya.user.management.svc.enums.Role;
import com.surya.user.management.svc.exceptions.UserAlreadyExistException;
import com.surya.user.management.svc.model.UserDetails;
import com.surya.user.management.svc.model.UserResponse;
import com.surya.user.management.svc.repository.UserRepository;

import jakarta.validation.Valid;

import java.util.Optional;

@Service
public class UserService {
	
	private final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private final UserRepository userRepo;
	
	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private final JWTService jwtService;

	public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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

		Optional<String> role = authentication.getAuthorities().stream()
				.findFirst()
				.map(GrantedAuthority::getAuthority);

		logger.info("Role details: {}", role);

		if(authentication.isAuthenticated() && role.isPresent() &&
				role.get().equalsIgnoreCase(Role.CUSTOMER.toString())){
			request.setRole(Role.CUSTOMER);
			String jwtToken = jwtService.generateJwtToken(request);
			logger.info("Jwt Token: {}", jwtToken);
			return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(jwtToken, true));
		}
		logger.error("Bad Credentials");
		throw new BadCredentialsException("Bad Credentials");

	}

}
