package com.arpon007.ArponPosSystem.payload.response;

import com.arpon007.ArponPosSystem.payload.dto.UserDto;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserDto user;

}
