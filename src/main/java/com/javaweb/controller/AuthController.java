package com.javaweb.controller;

import com.javaweb.model.*;
import com.javaweb.service.UserService;
import com.javaweb.service.impl.GoogleAuthService;
import com.javaweb.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.io.IOException;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class AuthController {
    @Value("${google.client.id}")
    private String clientId;


    @Value("${google.redirect.uri}")
    private String redirectUri;

    @Value("${frontend.redirect.uri}")
    private String frontendRedirectUri;


    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDTO request) {
        try {
            System.out.println("Received from FE: " + request);
            AuthResponse response = userService.registerUser(request);

            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.ok(response);
            }

        } catch (Exception e) {
            AuthResponse errorResponse = new AuthResponse(false, "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

//Have error
    @GetMapping("/google-login-url")
    public ResponseEntity<?> getGoogleLoginUrl() {
        String state = Base64.getEncoder().encodeToString(frontendRedirectUri.getBytes());
        String googleUrl = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + clientId +
                "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8) +
                "&response_type=code" +
                "&scope=email%20profile" +
                "&state=" + state;

        return ResponseEntity.ok(Map.of("url", googleUrl));
    }

    @GetMapping("/oauth2/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code) {
        try {
            GoogleUserInfo userInfo = googleAuthService.authenticateWithGoogle(code);

            if (userInfo == null || userInfo.getEmail() == null) {
                return ResponseEntity.badRequest().body(Map.of("message", "Missing email from Google"));
            }

            // Hàm này giờ đã trả GoogleLoginResponse rồi ✅
            GoogleLoginResponse loginResponse = googleAuthService.findOrCreateGoogleUser(userInfo);
            return ResponseEntity.ok(loginResponse);

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Google authentication failed", "error", e.getMessage()));
        }
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<?> sendOtp(@RequestBody EmailRequest emailRequest) {
        try {
            System.out.println("Received from FE: " + emailRequest);
            boolean isOtpSent = googleAuthService.sendOtpToEmail(emailRequest.getEmail());
            System.out.println(isOtpSent);
            if (isOtpSent) {

                return ResponseEntity.ok(Map.of("success", true, "message", "OTP sent to your email."));
            } else {
                return ResponseEntity.ok(Map.of("success", false, "message", "Email is not registered. Please sign up first."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error sending OTP."));
        }
    }

    @PostMapping("/verify-gmail")
    public ResponseEntity<?> verifyOtp(@RequestBody OTPRequest otpRequest) {
        boolean isVerified = googleAuthService.verifyOtp(otpRequest.getEmail(), otpRequest.getOtp());


        if (isVerified) {
            // Trả về success: true và thông báo
            return ResponseEntity.ok(Map.of("success", true, "message", "OTP verified successfully."));
        } else {
            // Trả về success: false và thông báo lỗi
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", "Invalid or expired OTP."));
        }
    }


    @GetMapping("/check-phone")
    public ResponseEntity<?> checkPhoneNumber(@RequestParam String phoneNumber) {
        try {
            boolean exists = userService.existsByPhoneNumber(phoneNumber);


            if (exists) {
                return ResponseEntity.ok(Map.of("exists", true,"message", "Phone number exists."));
            } else {
                return ResponseEntity.ok(Map.of("exists", false,"message", "Phone number not found."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("exists", false, "message", "Server error occurred."
                    ));
        }
    }





}

