package com.mercadolivre.apimlv2.usecases.registeruser;

import com.mercadolivre.apimlv2.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class RegisterUserController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/users")
    @Transactional
    public ResponseEntity<Void> registerUser(@RequestBody @Valid RegisterUserRequest request) {
        User user = request.toModel();
        manager.persist(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
