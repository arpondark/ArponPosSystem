package com.arpon007.ArponPosSystem.service.impl;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.arpon007.ArponPosSystem.Repo.UserRepository;
import com.arpon007.ArponPosSystem.config.JwtProvider;
import com.arpon007.ArponPosSystem.exception.UserException;
import com.arpon007.ArponPosSystem.models.User;
import com.arpon007.ArponPosSystem.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User getUserFromJwtToken(String token) throws UserException{
        // Remove "Bearer " prefix if present
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String email = jwtProvider.getEmailFromToken(token);
        if (email == null) {
            throw new UserException("Invalid or expired token");
        }

        return findUserByEmailOrThrow(email);
    }

    @Override
    public User getCurrentUser() throws UserException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return findUserByEmailOrThrow(email);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private User findUserByEmailOrThrow(String email) throws UserException {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UserException("User not found");
        }
        return user;
    }
}
