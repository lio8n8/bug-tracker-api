package com.app.bugtracker.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.app.bugtracker.dto.user.CreateUserDTO;
import com.app.bugtracker.repositories.IUsersRepository;

// TODO: Create BaseValidator
@Component
public class CreateUserValidator implements Validator {
    private final IUsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private Validator springValidator;

    @Autowired
    public CreateUserValidator(final IUsersRepository usersRepository,
            final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateUserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateUserDTO createUserDTO = (CreateUserDTO) target;

        if (!createUserDTO.getPsw().equals(createUserDTO.getConfirmPsw())) {
            errors.reject("Passwords are not matched.");
        }
        
        springValidator.validate(target, errors);
    }

}
