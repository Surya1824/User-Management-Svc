package com.surya.user.management.svc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.surya.user.management.svc.enums.Role;
import com.surya.user.management.svc.model.UserDetails;
import com.surya.user.management.svc.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	public String registerUser(@Valid UserDetails userdetails) {
		userdetails.setRole(Role.CUSTOMER);//default role
		userdetails.setPassword(passwordEncoder.encode(userdetails.getPassword()));
		userRepo.save(userdetails);
		return "User Register Successfully";
	}

}
