package com.mercadolivre.apimlv2.usecases.registercategory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mercadolivre.apimlv2.domain.Category;

import javax.validation.constraints.NotBlank;

public class NewCategoryRequest {
    @NotBlank
    private String name;
    private Long parentCategoryId;

    @JsonCreator
    public NewCategoryRequest(@NotBlank String name, Long parentCategoryId) {
        this.name = name;
        this.parentCategoryId = parentCategoryId;
    }

    public Category toModel() {
        return new Category(this.name);
    }

    @Override
    public String toString() {
        return "NewCategoryRequest{" +
                "name='" + name + '\'' +
                ", parentCategoryId=" + parentCategoryId +
                '}';
    }
}
