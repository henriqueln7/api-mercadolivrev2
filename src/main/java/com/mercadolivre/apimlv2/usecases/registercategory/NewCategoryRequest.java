package com.mercadolivre.apimlv2.usecases.registercategory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mercadolivre.apimlv2.domain.Category;

import javax.validation.constraints.NotBlank;
import java.util.function.Function;

public class NewCategoryRequest {
    @NotBlank
    public final String name;
    public final Long parentCategoryId;

    @JsonCreator
    public NewCategoryRequest(@NotBlank String name, Long parentCategoryId) {
        this.name = name;
        this.parentCategoryId = parentCategoryId;
    }

    public Category toModel(Function<Long, Category> findCategoryById) {
        Category category = new Category(this.name);
        if (this.parentCategoryId != null) {
            Category parentCategory = findCategoryById.apply(this.parentCategoryId);
            category.addParentCategory(parentCategory);
        }
        return category;
    }

    @Override
    public String toString() {
        return "NewCategoryRequest{" +
                "name='" + name + '\'' +
                ", parentCategoryId=" + parentCategoryId +
                '}';
    }

    /**
     *
     * @return category name, so SpringMVC can build errors properly
     */
    public String getName() {
        return name;
    }
}
