package com.mercadolivre.apimlv2.usecases.registerproduct;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

public class RegisterProductRequest {
    @NotBlank
    private final String name;
    @NotNull
    @Positive
    private final BigDecimal price;
    @Positive
    private final int amountAvailable;
    @Size(min = 3)
    private final Set<String> features;
    @NotBlank
    @Length(max = 1000)
    private final String description;
    @NotNull
    private final Long categoryId;

    public RegisterProductRequest(@NotBlank String name, @NotNull @Positive BigDecimal price, @Positive int amountAvailable, @Size(min = 3) Set<String> features, @NotBlank @Length(max = 1000) String description, @NotNull Long categoryId) {
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
}
