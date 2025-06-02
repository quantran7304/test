package com.javaweb.controller;

import com.javaweb.model.GoogleUserInfo;
import com.javaweb.model.LoginRequest;
import com.javaweb.model.LoginResponse;
import com.javaweb.model.UserDTO;
import com.javaweb.service.UserService;
import com.javaweb.service.impl.GoogleAuthService;
import com.javaweb.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@RequestBody UserDTO request) {
        try {
            userService.registerUser(request);
            return ResponseEntity.ok("Succes");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

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
    public void handleGoogleCallback(
            @RequestParam String code,
            @RequestParam(required = false) String state,
            HttpServletResponse response) throws IOException {

        GoogleUserInfo userInfo = googleAuthService.authenticateWithGoogle(code);

        if (userInfo == null || userInfo.getEmail() == null) {
            response.sendRedirect(frontendRedirectUri + "?error=missing_user");
            return;
        }

        String token = JwtUtil.generateToken(userInfo.getEmail());
        String redirectUrl = frontendRedirectUri + "?token=" + token;
        response.sendRedirect(redirectUrl);
    }


}
