package com.mercadolivre.apimlv2.usecases.registerproduct;

import com.mercadolivre.apimlv2.domain.Category;
import com.mercadolivre.apimlv2.domain.User;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @NotNull @Positive
    private BigDecimal price;
    @Positive
    private int amountAvailable;
    @Size(min = 3)
    @ElementCollection
    private Set<String> features;
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

    public Product(@NotBlank String name, @NotNull @Positive BigDecimal price, @Positive int amountAvailable, @Size(min = 3) Set<String> features, @NotBlank @Length(max = 1000) String description, @Valid @NotNull Category category, @Valid @NotNull User owner) {
        this.name = name;
        this.price = price;
        this.amountAvailable = amountAvailable;
        this.features = features;
        this.description = description;
        this.category = category;
        this.owner = owner;
        this.createdAt = LocalDateTime.now();
    }
}
