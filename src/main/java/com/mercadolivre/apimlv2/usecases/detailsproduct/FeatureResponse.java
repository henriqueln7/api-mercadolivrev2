package com.mercadolivre.apimlv2.usecases.detailsproduct;

import com.mercadolivre.apimlv2.domain.ProductFeature;

class FeatureResponse {

    public final String name;
    public final String description;

    public FeatureResponse(ProductFeature feature) {
        this.name = feature.getName();
        this.description = feature.getDescription();
    }
}
