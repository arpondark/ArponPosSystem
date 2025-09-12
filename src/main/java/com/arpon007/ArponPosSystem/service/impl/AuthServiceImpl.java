package com.arpon007.ArponPosSystem.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arpon007.ArponPosSystem.Repo.UserRepository;
import com.arpon007.ArponPosSystem.config.JwtProvider;
import com.arpon007.ArponPosSystem.exception.UserException;
import com.arpon007.ArponPosSystem.mapper.UserMapper;
import com.arpon007.ArponPosSystem.models.User;
import com.arpon007.ArponPosSystem.models.UserRole;
import com.arpon007.ArponPosSystem.payload.dto.UserDto;
import com.arpon007.ArponPosSystem.payload.response.AuthResponse;
import com.arpon007.ArponPosSystem.service.AuthService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomerUserImplemantation customerUserImplemantation;

    @Override
    public AuthResponse signup(UserDto userDto) throws UserException {
        if(userDto.getRole() == UserRole.ROLE_ADMIN){
            throw new UserException("Cannot Register as Admin");
        }
        return createUser(userDto, "User Registered Successfully");
    }

    @Override
    public AuthResponse login(UserDto userDto) throws UserException {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        User user = userRepository.findByEmail(email);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("User Login Successfully");
        authResponse.setUser(UserMapper.toDto(user));
        return authResponse;
    }

    @Override
    public AuthResponse signupAdmin(UserDto userDto) throws UserException {
        userDto.setRole(UserRole.ROLE_ADMIN); // Force admin role
        return createUser(userDto, "Admin User Registered Successfully");
    }

    private AuthResponse createUser(UserDto userDto, String successMessage) throws UserException {
        User user = userRepository.findByEmail(userDto.getEmail());
        if(user != null){
            throw new UserException("Email Already Exist");
        }

        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole(userDto.getRole());
        newUser.setFullName(userDto.getFullName());
        newUser.setPhone(userDto.getPhone());
        userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage(successMessage);
        authResponse.setUser(UserMapper.toDto(newUser));
        return authResponse;
    }

    private Authentication authenticate(String email, String password) throws UserException {
        UserDetails userDetails = customerUserImplemantation.loadUserByUsername(email);
        if(userDetails == null) {
            throw new UserException("User Not Found");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UserException("Password is incorrect");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }
}
