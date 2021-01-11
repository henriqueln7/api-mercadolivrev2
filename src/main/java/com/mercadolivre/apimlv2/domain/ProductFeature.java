package com.mercadolivre.apimlv2.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductFeature that = (ProductFeature) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
