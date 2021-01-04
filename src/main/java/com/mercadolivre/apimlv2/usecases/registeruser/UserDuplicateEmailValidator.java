package com.mercadolivre.apimlv2.usecases.registeruser;

import com.mercadolivre.apimlv2.domain.UserRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserDuplicateEmailValidator implements Validator {

    private final UserRepository userRepository;

    public UserDuplicateEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterUserRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        RegisterUserRequest request = (RegisterUserRequest) target;
        if (userRepository.findByLogin(request.login).isPresent()) {
            errors.rejectValue("login", "", "Email already exists");
        }
    }
}
