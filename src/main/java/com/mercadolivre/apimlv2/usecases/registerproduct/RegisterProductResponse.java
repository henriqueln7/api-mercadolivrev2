package com.mercadolivre.apimlv2.usecases.registerproduct;

import com.mercadolivre.apimlv2.domain.Category;
import com.mercadolivre.apimlv2.domain.Product;
import com.mercadolivre.apimlv2.domain.ProductFeature;
import com.mercadolivre.apimlv2.domain.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class RegisterProductResponse {
    public final Long id;
    public final String name;
    public final BigDecimal price;
    public final int amountAvailable;
    public final List<FeatureResponse> features;
    public final String description;
    public final CategoryResponse category;
    public final OwnerResponse owner;
    public final LocalDateTime createdAt;

    public RegisterProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.amountAvailable = product.getAmountAvailable();
        this.features = product.getFeatures().stream().map(feature -> new FeatureResponse(feature)).collect(Collectors.toList());
        this.description = product.getDescription();
        this.category = new CategoryResponse(product.getCategory());
        this.owner = new OwnerResponse(product.getOwner());
        this.createdAt = product.getCreatedAt();
    }

}

class FeatureResponse {

    public final String name;
    public final String description;

    public FeatureResponse(ProductFeature feature) {
        this.name = feature.getName();
        this.description = feature.getDescription();
    }
}

class CategoryResponse {

    public final String name;

    public CategoryResponse(Category category) {
        this.name = category.getName();
    }
}

class OwnerResponse {

    public final String email;

    public OwnerResponse(User owner) {
        this.email = owner.getLogin();
    }
}
