package com.mercadolivre.apimlv2.usecases.detailsproduct;

import com.mercadolivre.apimlv2.domain.Category;

class CategoryResponse {

    public final String name;

    public CategoryResponse(Category category) {
        this.name = category.getName();
    }
}
