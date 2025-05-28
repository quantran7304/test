package com.javaweb.service;

import com.javaweb.model.LoginRequest;
import com.javaweb.model.LoginResponse;

public interface UserService {
    public LoginResponse login(LoginRequest request);
}
