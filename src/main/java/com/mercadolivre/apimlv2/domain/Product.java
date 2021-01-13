package com.mercadolivre.apimlv2.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private final Set<ProductImage> images = new HashSet<>();

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

    public Long getId() {
        Assert.notNull(this.id, "ID is null. Perhaps object was not persisted");
        return this.id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public int getAmountAvailable() {
        return amountAvailable;
    }

    public User getOwner() {
        return owner;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<ProductFeature> getFeatures() {
        return Collections.unmodifiableSet(features);
    }

    public boolean belongsTo(User owner) {
        return this.owner.equals(owner);
    }

    public void addImagesUrl(@NotNull @Size(min = 1) Set<String> imagesUrl) {
        Assert.isTrue(!imagesUrl.isEmpty(), "The set has to contain at least 1 element");
        Set<ProductImage> images = imagesUrl.stream()
                                             .map(imageUrl -> new ProductImage(this, imageUrl))
                                             .collect(Collectors.toSet());
        this.images.addAll(images);
    }

}

