package com.mercadolivre.apimlv2.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @NotNull @Positive
    private BigDecimal price;
    @Positive
    private int amountAvailable;
    @Size(min = 3) @Valid
    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<ProductFeature> features = new HashSet<>();
    @NotBlank @Length(max = 1000)
    private String description;
    @Valid @NotNull
    @ManyToOne
    private Category category;
    @Valid @NotNull
    @ManyToOne
    private User owner;
    private LocalDateTime createdAt;

    @Deprecated
    protected Product(){}

    public Product(@NotBlank String name, @NotNull @Positive BigDecimal price, @Positive int amountAvailable, @Size(min = 3) @Valid Set<ProductFeature> features, @NotBlank @Length(max = 1000) String description, @Valid @NotNull Category category, @Valid @NotNull User owner) {
        this.name = name;
        this.price = price;
        this.amountAvailable = amountAvailable;
        this.features.addAll(features);
        this.description = description;
        this.category = category;
        this.owner = owner;
        this.createdAt = LocalDateTime.now();
    }
}

