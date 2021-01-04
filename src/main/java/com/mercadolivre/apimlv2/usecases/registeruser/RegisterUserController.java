package com.mercadolivre.apimlv2.usecases.registeruser;

import com.mercadolivre.apimlv2.domain.User;
import com.mercadolivre.apimlv2.domain.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class RegisterUserController {

    private final UserRepository userRepository;

    public RegisterUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(new UserDuplicateEmailValidator(userRepository));
    }

    @PostMapping("/users")
    @Transactional
    public ResponseEntity<Void> registerUser(@RequestBody @Valid RegisterUserRequest request) {
        User user = request.toModel();
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
