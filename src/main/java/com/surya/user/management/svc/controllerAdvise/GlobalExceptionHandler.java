package com.surya.user.management.svc.controllerAdvise;

import com.surya.user.management.svc.exceptions.RoleMismatchError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.surya.user.management.svc.exceptions.UserAlreadyExistException;
import com.surya.user.management.svc.model.UserResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<UserResponse> handleUserExists(UserAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new UserResponse(ex.getMessage(), false));
    }

    @ExceptionHandler(RoleMismatchError.class)
    public ResponseEntity<UserResponse> handleRoleMismatch(RoleMismatchError re){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new UserResponse("RoleMismatch", re.getMessage()));
    }

}
