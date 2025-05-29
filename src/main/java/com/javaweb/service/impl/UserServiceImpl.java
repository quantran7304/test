package com.javaweb.service.impl;

import com.javaweb.model.LoginRequest;
import com.javaweb.model.LoginResponse;
import com.javaweb.model.UserDTO;
import com.javaweb.repository.entity.UserEntity;
import com.javaweb.repository.impl.UserRepositoryImpl;
import com.javaweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepositoryImpl userRepo;

    @Override
    public LoginResponse login(LoginRequest request) {
        Optional<UserEntity> userOpt = userRepo.findByEmail(request.getEmail());
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(request.getPassword())) {
            return new LoginResponse(true, "Login success");
        }
        return new LoginResponse(false, "Invalid credentials");
    }
}
