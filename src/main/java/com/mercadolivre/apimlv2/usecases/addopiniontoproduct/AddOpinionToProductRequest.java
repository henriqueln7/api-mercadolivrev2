package com.mercadolivre.apimlv2.usecases.addopiniontoproduct;

import com.mercadolivre.apimlv2.domain.Opinion;
import com.mercadolivre.apimlv2.domain.Product;
import com.mercadolivre.apimlv2.domain.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AddOpinionToProductRequest {
    @Min(1)
    @Max(5)
    private final int score;
    @NotBlank
    private final String title;
    @NotBlank
    @Size(max = 500)
    private final String description;

    public AddOpinionToProductRequest(@Min(1) @Max(5) int score, @NotBlank String title, @NotBlank @Size(max = 500) String description) {
        this.score = score;
        this.title = title;
        this.description = description;
    }

    public Opinion toModel(Product product, User opinionator) {
        return new Opinion(this.score, this.title, this.description, product, opinionator);
    }
}
