package com.doanjava.gradehub.service;

import com.doanjava.gradehub.dto.LoginRequest;
import com.doanjava.gradehub.dto.LoginResponse;
import com.doanjava.gradehub.dto.RegisterRequest;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    LoginResponse register(RegisterRequest registerRequest);
}
