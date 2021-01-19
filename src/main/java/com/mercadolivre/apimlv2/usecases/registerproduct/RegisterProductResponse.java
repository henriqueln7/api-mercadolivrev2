package com.mercadolivre.apimlv2.usecases.registerproduct;

import com.mercadolivre.apimlv2.domain.*;

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
    public final List<String> images;
    public final List<OpinionResponse> opinions;
    public final int amountOfOpinions;
    public final double scoreAverage;

    public RegisterProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.amountAvailable = product.getAmountAvailable();
        this.features = product.getFeatures().stream().map(FeatureResponse::new).collect(Collectors.toList());
        this.description = product.getDescription();
        this.category = new CategoryResponse(product.getCategory());
        this.owner = new OwnerResponse(product.getOwner());
        this.createdAt = product.getCreatedAt();
        this.images = product.getImages().stream().map(ProductImage::getImageUrl).collect(Collectors.toList());
        this.opinions = product.getOpinions().stream().map(OpinionResponse::new).collect(Collectors.toList());
        this.amountOfOpinions = product.getOpinions().size();
        this.scoreAverage = product.getOpinions().stream().mapToInt(Opinion::getScore).average().getAsDouble();
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

class QuestionResponse {
    public QuestionResponse(Question question) {
    }
}

class OpinionResponse {

    public final String title;
    public final String description;
    public final String opinionatorEmail;
    public final int score;

    public OpinionResponse(Opinion opinion) {
        title = opinion.getTitle();
        description = opinion.getDescription();
        opinionatorEmail = opinion.getOpinionator().getLogin();
        score = opinion.getScore();
    }
}