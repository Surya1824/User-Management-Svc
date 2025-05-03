package com.surya.user.management.svc.controller;

import com.surya.user.management.svc.enums.Role;
import com.surya.user.management.svc.exceptions.UserAlreadyExistException;
import com.surya.user.management.svc.model.LoginRequest;
import com.surya.user.management.svc.model.LoginResponse;
import com.surya.user.management.svc.model.UserDetails;
import com.surya.user.management.svc.model.UserResponse;
import com.surya.user.management.svc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerAdmin(@RequestBody @Valid UserDetails userdetails) throws UserAlreadyExistException {
        return userService.registerUser(userdetails,Role.ADMIN);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> adminLogin(@RequestBody @Valid LoginRequest request){
        return userService.verifyUser(request, Role.ADMIN);
    }

}
