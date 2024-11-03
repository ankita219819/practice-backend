package com.complete.cure.app.service;

import com.complete.cure.app.dto.OTPRequest;
import com.complete.cure.app.dto.SignupRequest;
import com.complete.cure.app.exception.UserAlreadyExistsException;
import com.complete.cure.app.exception.InvalidOTPException;
import com.complete.cure.app.exception.UserNotFoundException;
import com.complete.cure.app.model.User;
import com.complete.cure.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;

@Service
public class AuthService {
    private final static int OTP_LENGTH = 6;

    @Autowired
    private UserRepository userRepository;

    // Simulate OTP storage
    private String generatedOtp;
    private String otpIdentifier;
    private LocalDateTime otpExpiryTime;

    public void signup(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()) != null ||
                userRepository.findByPhoneNumber(request.getPhoneNumber()) != null) {
            throw new UserAlreadyExistsException("User with this email or phone number already exists.");
        }

        User user = new User();
        user.setUserId(generateUserId());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPasswordHash(""); // Placeholder until password is set
        user.setRole("User");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setVerified(false);

        userRepository.save(user);
        sendOtp(user.getEmail()); // Send OTP via email
    }

    public void verifyOtp(OTPRequest otpRequest) {
        if (otpRequest.getOtp().equals(generatedOtp) && LocalDateTime.now().isBefore(otpExpiryTime)) {
            User user = userRepository.findByEmail(otpRequest.getIdentifier());
            if (user == null) {
                user = userRepository.findByPhoneNumber(otpRequest.getIdentifier());
            }
            if (user != null) {
                user.setVerified(true);
                userRepository.save(user);
                return;
            }
        }
        throw new InvalidOTPException("Invalid or expired OTP.");
    }

    public void setPassword(String userId, String password) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));
        // Password validation logic can go here
        user.setPasswordHash(hashPassword(password)); // Implement password hashing
        userRepository.save(user);
    }

    private void sendOtp(String email) {
        // Generate OTP
        generatedOtp = generateOtp();
        otpIdentifier = email; // Store the identifier (email or phone)
        otpExpiryTime = LocalDateTime.now().plusMinutes(5); // 5-minute expiration
        // Simulate sending OTP
        System.out.println("OTP sent to " + email + ": " + generatedOtp);
    }

    private String generateOtp() {
        Random random = new SecureRandom();
        StringBuilder otpBuilder = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otpBuilder.append(random.nextInt(10)); // Digits from 0-9
        }
        return otpBuilder.toString();
    }

    private String generateUserId() {
        return Base64.getUrlEncoder().encodeToString(String.valueOf(System.nanoTime()).getBytes());
    }

    private String hashPassword(String password) {
        // Implement password hashing using a secure hashing algorithm
        return password; // Placeholder, implement actual hashing
    }
}