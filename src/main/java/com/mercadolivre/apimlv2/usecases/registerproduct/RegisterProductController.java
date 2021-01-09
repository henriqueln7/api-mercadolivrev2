package com.mercadolivre.apimlv2.usecases.registerproduct;

import com.mercadolivre.apimlv2.domain.User;
import com.mercadolivre.apimlv2.security.LoggedUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class RegisterProductController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/products")
    @Transactional
    public void registerProduct(@RequestBody @Valid RegisterProductRequest request, @AuthenticationPrincipal LoggedUser loggedUser) {
        User productOwner = loggedUser.get();
        Product newProduct = request.toModel(productOwner, manager);
        manager.persist(newProduct);
    }
}