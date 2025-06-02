package com.javaweb.service;

import com.javaweb.model.LoginRequest;
import com.javaweb.model.LoginResponse;
import com.javaweb.model.UserDTO;
import org.apache.catalina.User;

public interface UserService {
    public LoginResponse login(LoginRequest request);
    public boolean registerUser(UserDTO user);
}
