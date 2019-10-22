package com.app.bugtracker.converters;

import org.springframework.core.convert.converter.Converter;

import com.app.bugtracker.dto.user.UserDTO;
import com.app.bugtracker.models.user.User;

public class UserConverter implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User source) {
        return UserDTO.builder()
                .id(source.getId())
                .email(source.getEmail())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}
