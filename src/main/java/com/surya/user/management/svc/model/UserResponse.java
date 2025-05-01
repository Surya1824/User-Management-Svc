package com.surya.user.management.svc.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
	
	private String message;
    private boolean registered;
    private Long userId; // Optional

    public UserResponse(String message, boolean success) {
        this.message = message;
        this.registered = success;
    }

    public UserResponse(String message, boolean success, Long userId) {
        this.message = message;
        this.registered = success;
        this.userId = userId;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
