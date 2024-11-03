package com.complete.cure.app.controller;

import com.complete.cure.app.dto.OTPRequest;
import com.complete.cure.app.dto.SignupRequest;
import com.complete.cure.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok("Signup successful. OTP sent for verification.");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OTPRequest otpRequest) {
        authService.verifyOtp(otpRequest);
        return ResponseEntity.ok("OTP verified successfully.");
    }

    @PostMapping("/set-password/{userId}")
    public ResponseEntity<String> setPassword(@PathVariable String userId, @RequestBody String password){
        authService.setPassword(userId, password);
        return ResponseEntity.ok("Password set successfully.");
    }

}