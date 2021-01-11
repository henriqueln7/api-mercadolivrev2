package com.mercadolivre.apimlv2.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class ProductFeature {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    private String description;

    @Deprecated
    protected ProductFeature() {
    }

    public ProductFeature(@NotBlank String name, @NotBlank String description) {
        this.name = name;
        this.description = description;
    }
}
