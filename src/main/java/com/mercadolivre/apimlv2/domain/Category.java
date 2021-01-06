package com.mercadolivre.apimlv2.domain;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @ManyToOne
    private Category parentCategory;

    @Deprecated
    protected Category(){}

    public Category(@NotBlank String name) {
        Assert.hasText(name, "You should pass a not blank name");
        this.name = name;
    }

    public void addParentCategory(Category parentCategory) {
        Assert.notNull(parentCategory, "You should not pass a null parentCategory");
        Assert.isTrue(Optional.ofNullable(this.parentCategory).isEmpty(), "This category already has a parent");
        this.parentCategory = parentCategory;
    }
}
