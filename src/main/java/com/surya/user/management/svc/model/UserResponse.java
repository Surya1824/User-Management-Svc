package com.surya.user.management.svc.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

	private String error;
	private String message;
    private boolean success;
    private Long userId; // Optional

    public UserResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public UserResponse(String message, boolean success, Long userId) {
        this.message = message;
        this.success = success;
        this.userId = userId;
    }

	public UserResponse(String error, String message) {
		this.error = error;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
