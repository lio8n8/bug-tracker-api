package com.app.bugtracker.users.converters;

import com.app.bugtracker.users.dto.UserDTO;
import com.app.bugtracker.users.models.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts User to UserDTO.
 */
@Component
public class UserToUserDTOConverter implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User source) {
        return UserDTO.builder()
                .id(source.getId())
                .email(source.getEmail())
                .username(source.getUsername())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .locked(source.getLocked())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())
                .activatedAt(source.getActivatedAt())
                .lastLogin(source.getLastLogin())
                .build();
    }
}
