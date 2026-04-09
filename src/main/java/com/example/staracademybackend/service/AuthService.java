package com.example.staracademybackend.service;

import com.example.staracademybackend.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    public String authenticate(String username, String password) {
        if (adminUsername.equals(username) && adminPassword.equals(password)) {
            return jwtUtils.generateToken(username);
        }
        throw new RuntimeException("Invalid credentials");
    }
}
