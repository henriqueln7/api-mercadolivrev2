package com.mercadolivre.apimlv2.usecases.registerproduct;

import com.mercadolivre.apimlv2.domain.Category;
import com.mercadolivre.apimlv2.domain.Product;
import com.mercadolivre.apimlv2.domain.ProductFeature;
import com.mercadolivre.apimlv2.domain.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class RegisterProductRequest {
    @NotBlank
    private final String name;
    @NotNull
    @Positive
    private final BigDecimal price;
    @Positive
    private final int amountAvailable;
    private final Set<NewFeatureRequest> features;
    @NotBlank
    @Length(max = 1000)
    private final String description;
    @NotNull
    private final Long categoryId;

    public RegisterProductRequest(@NotBlank String name, @NotNull @Positive BigDecimal price, @Positive int amountAvailable, Set<NewFeatureRequest> features, @NotBlank @Length(max = 1000) String description, @NotNull Long categoryId) {
        this.name = name;
        this.price = price;
        this.amountAvailable = amountAvailable;
        this.features = features;
        this.description = description;
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "RegisterProductRequest{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", amountAvailable=" + amountAvailable +
                ", features=" + features +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }

    public Product toModel(@Valid @NotNull User owner, EntityManager manager) {
        Category category = manager.find(Category.class, this.categoryId);
        Assert.notNull(category, "Category cannot be null");

        Set<ProductFeature> features = this.features.stream()
                                                    .map(NewFeatureRequest::toModel)
                                                    .collect(Collectors.toSet());
        return new Product(this.name, this.price, this.amountAvailable, features, this.description, category, owner);
    }
}

class NewFeatureRequest {
    @NotBlank
    private final String name;
    @NotBlank
    private final String description;

    public NewFeatureRequest(@NotBlank String name, @NotBlank String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewFeatureRequest that = (NewFeatureRequest) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public ProductFeature toModel() {
        return new ProductFeature(this.name, this.description);
    }
}