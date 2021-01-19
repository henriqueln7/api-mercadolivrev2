package com.mercadolivre.apimlv2.usecases.detailsproduct;

import com.mercadolivre.apimlv2.domain.Product;
import com.mercadolivre.apimlv2.usecases.registerproduct.RegisterProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
public class DetailsProductController {

    @PersistenceContext
    private EntityManager manager;

    @GetMapping("/products/{productId}")
    public ResponseEntity<RegisterProductResponse> detailsProduct(@PathVariable("productId") Long productId) {
        Product product = manager.find(Product.class, productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new RegisterProductResponse(product));
    }
}
