package com.mercadolivre.apimlv2.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
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
    @PositiveOrZero
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
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private List<Opinion> opinions = new ArrayList<>();

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

    public Set<ProductImage> getImages() {
        return Collections.unmodifiableSet(images);
    }

    public List<Opinion> getOpinions() {
        return Collections.unmodifiableList(opinions);
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

    public void addOpinion(@Valid @NotNull Opinion opinion) {
        Assert.notNull(opinion, "Opinion is null #BUG");
        this.opinions.add(opinion);
    }

    /**
     * Beat stock given the amount.
     * @param amountToBeat Amount to beat.
     * @return true, if operation was successful. False otherwise
     */
    public boolean beatStock(@Positive int amountToBeat) {
        Assert.isTrue(amountToBeat > 0, "You need to pass a positive amount");

        if (this.amountAvailable >= amountToBeat) {
            this.amountAvailable -= amountToBeat;
            return true;
        }
        return false;
    }
}

