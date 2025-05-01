package com.surya.user.management.svc.exceptions;

@SuppressWarnings("serial")
public class UserAlreadyExistException extends Exception {

	public UserAlreadyExistException(String errorMessage) {
		super(errorMessage);
	}

}
