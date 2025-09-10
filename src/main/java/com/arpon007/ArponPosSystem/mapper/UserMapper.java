package com.arpon007.ArponPosSystem.mapper;

import com.arpon007.ArponPosSystem.models.User;
import com.arpon007.ArponPosSystem.payload.dto.UserDto;

public class UserMapper {
    public static UserDto toDto(User newUser) {
        UserDto userDto = new UserDto();
        userDto.setId(newUser.getId());
        userDto.setFullName(newUser.getFullName());
        userDto.setEmail(newUser.getEmail());
        userDto.setPhone(newUser.getPhone());
        userDto.setRole(newUser.getRole());
        userDto.setCreatedAt(newUser.getCreatedAt());
        userDto.setUpdatedAt(newUser.getUpdatedAt());
        userDto.setLastLogin(newUser.getLastLogin());
        return userDto;
    }
}
