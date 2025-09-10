package com.arpon007.ArponPosSystem.controller;

import com.arpon007.ArponPosSystem.exception.UserException;
import com.arpon007.ArponPosSystem.payload.dto.UserDto;
import com.arpon007.ArponPosSystem.payload.response.AuthResponse;
import com.arpon007.ArponPosSystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signupHandler(@RequestBody UserDto userDto) throws UserException {
        AuthResponse response = authService.signup(userDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody UserDto userDto) throws UserException {
        AuthResponse response = authService.login(userDto);
        return ResponseEntity.ok(response);
    }
}
