package com.mercadolivre.apimlv2.usecases.registerproduct;

import com.mercadolivre.apimlv2.domain.Product;
import com.mercadolivre.apimlv2.domain.User;
import com.mercadolivre.apimlv2.security.LoggedUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class RegisterProductController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/products")
    @Transactional
    public ResponseEntity<Void> registerProduct(@RequestBody @Valid RegisterProductRequest request, @AuthenticationPrincipal LoggedUser loggedUser, UriComponentsBuilder uriComponentsBuilder) {
        User productOwner = loggedUser.get();
        Product newProduct = request.toModel(productOwner, manager);
        manager.persist(newProduct);

        URI url = uriComponentsBuilder.path("/products/{id}").buildAndExpand(newProduct.getId()).toUri();
        return ResponseEntity.created(url).build();
    }
}
