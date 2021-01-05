package com.mercadolivre.apimlv2.usecases.registeruser;

import com.mercadolivre.apimlv2.domain.CleanPassword;
import com.mercadolivre.apimlv2.domain.User;
import com.mercadolivre.apimlv2.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDuplicateEmailValidatorTest {

    @Test
    @DisplayName("It should reject value if email already exists")
    void itShouldRejectValueIfEmailAlreadyExists() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        Object target = new RegisterUserRequest("henrique@gmail.com", "abc123");
        UserDuplicateEmailValidator validator = new UserDuplicateEmailValidator(userRepository);
        Errors errors = new BeanPropertyBindingResult(target, "test");

        Mockito.when(userRepository.findByLogin("henrique@gmail.com")).thenReturn(Optional.of(new User("henrique@gmail.com", new CleanPassword("abc123"))));

        validator.validate(target, errors);

        assertTrue(errors.hasFieldErrors("login"));
    }

    @Test
    @DisplayName("It should not reject if email not exists in the system")
    void itShouldNotRejectIfEmailNotExistsInTheSystem() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        Object target = new RegisterUserRequest("henrique@gmail.com", "abc123");
        UserDuplicateEmailValidator validator = new UserDuplicateEmailValidator(userRepository);
        Errors errors = new BeanPropertyBindingResult(target, "test");

        Mockito.when(userRepository.findByLogin("henrique@gmail.com")).thenReturn(Optional.empty());

        validator.validate(target, errors);

        assertFalse(errors.hasFieldErrors("login"));

    }

}