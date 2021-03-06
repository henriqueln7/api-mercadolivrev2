package com.mercadolivre.apimlv2.usecases.detailsproduct;

import com.mercadolivre.apimlv2.domain.Opinion;
import com.mercadolivre.apimlv2.domain.Product;
import com.mercadolivre.apimlv2.domain.ProductImage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DetailProductResponse {
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

    public DetailProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.amountAvailable = product.getAmountAvailable();
        this.features = product.getFeatures().stream()
                               .map(FeatureResponse::new)
                               .collect(Collectors.toList());
        this.description = product.getDescription();
        this.category = new CategoryResponse(product.getCategory());
        this.owner = new OwnerResponse(product.getOwner());
        this.createdAt = product.getCreatedAt();
        this.images = product.getImages().stream()
                             .map(ProductImage::getImageUrl)
                             .collect(Collectors.toList());
        this.opinions = product.getOpinions().stream()
                               .map(OpinionResponse::new)
                               .collect(Collectors.toList());
        this.amountOfOpinions = product.getOpinions().size();
        this.scoreAverage = product.getOpinions().stream()
                                   .mapToInt(Opinion::getScore)
                                   .average().orElse(0);
    }
}
