package com.surya.user.management.svc.model;

public class LoginResponse {

    private String jwtToken;
    private boolean verified;

    public LoginResponse(String jwtToken, boolean verified) {
        this.jwtToken = jwtToken;
        this.verified = verified;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }


}
