package com.mercadolivre.apimlv2.domain;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @Valid
    @ManyToOne
    private Product product;
    @NotNull @URL
    private String imageUrl;

    @Deprecated
    protected ProductImage(){}

    public ProductImage(@NotNull @Valid Product product, @NotNull @URL String imageUrl) {

        this.product = product;
        this.imageUrl = imageUrl;
    }
}
