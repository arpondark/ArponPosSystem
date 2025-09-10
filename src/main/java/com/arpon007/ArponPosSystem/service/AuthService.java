package com.arpon007.ArponPosSystem.service;

import com.arpon007.ArponPosSystem.exception.UserException;
import com.arpon007.ArponPosSystem.payload.dto.UserDto;
import com.arpon007.ArponPosSystem.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(UserDto userDto) throws UserException;
    AuthResponse login(UserDto userDto) throws UserException;
}
