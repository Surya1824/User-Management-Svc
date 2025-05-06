package com.surya.user.management.svc.controller;

import com.surya.user.management.svc.enums.Role;
import com.surya.user.management.svc.exceptions.RoleMismatchError;
import com.surya.user.management.svc.model.LoginRequest;
import com.surya.user.management.svc.model.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public String greetCustomer(@RequestHeader(name = "User-Type") String userType) throws RoleMismatchError {
		System.out.println("User type: " + userType);
		if(userType.equalsIgnoreCase(Role.CUSTOMER.name())){
			return "Hello, How is your Day?";
		}else {
			throw new RoleMismatchError("You do not have the required role to access this resource.");
		}

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
