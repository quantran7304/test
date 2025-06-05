package com.javaweb.service.impl;

import com.javaweb.model.LoginRequest;
import com.javaweb.model.LoginResponse;
import com.javaweb.model.UserDTO;
import com.javaweb.repository.entity.RoleEntity;
import com.javaweb.repository.entity.UserEntity;
import com.javaweb.repository.impl.UserRepositoryImpl;
import com.javaweb.service.UserService;
import org.apache.catalina.User;
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
        LoginResponse response;

        if (userOpt.isPresent()) {
            String passDb = userOpt.get().getPassword();
            String passFe = request.getPassword();

            if (passDb != null && passDb.equals(passFe)) {
                return response = new LoginResponse(true, "Login success");
            } else {
                return response = new LoginResponse(false, "Invalid credentials");
            }
        }

        return new LoginResponse(false, "User not found");
    }

    @Override
    public boolean registerUser(UserDTO request) {
        if(userRepo.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email was exist");
        }
        if(userRepo.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("PhoneNumber was exist");
        }

        RoleEntity defaultRole = new RoleEntity();
        defaultRole.setRoleId(2);

        UserEntity user = new UserEntity(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPhone(),
                request.getPassword(),
                defaultRole
        );

        userRepo.save(user);
        return true;
    }

}
