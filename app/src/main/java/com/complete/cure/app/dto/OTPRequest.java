package com.complete.cure.app.dto;

public class OTPRequest {
    private String identifier; // email or phone number
    private String otp;

    // Getters and Setters

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}