package com.mercadolivre.apimlv2.usecases.addopiniontoproduct;

import com.mercadolivre.apimlv2.domain.Opinion;
import com.mercadolivre.apimlv2.domain.Product;
import com.mercadolivre.apimlv2.domain.User;
import com.mercadolivre.apimlv2.security.LoggedUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class AddOpinionToProductController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/products/{productId}/opinions")
    @Transactional
    public void addOpinion(@PathVariable("productId") Long productId,
                           @Valid @RequestBody AddOpinionToProductRequest request,
                           @AuthenticationPrincipal LoggedUser loggedUser) {
        Product product = manager.find(Product.class, productId);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with passed ID not found");
        }
        User opinionator = loggedUser.get();

        Opinion opinion = request.toModel(product, opinionator);

        product.addOpinion(opinion);
        manager.merge(product);
    }
}
