package com.mercadolivre.apimlv2.usecases.addimagetoproduct;

import com.mercadolivre.apimlv2.domain.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AddRepository {
    @PersistenceContext
    private EntityManager manager;

    public Product find(Long productId) {
        return manager.find(Product.class, productId);
    }

}
