package com.mercadolivre.apimlv2.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Entity
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1) @Max(5)
    private int score;
    @NotBlank
    private String title;
    @NotBlank @Size(max = 500)
    private String description;
    @NotNull @Valid
    @ManyToOne
    private Product product;
    @NotNull @Valid
    @ManyToOne
    private User opinionator;

    @Deprecated
    protected Opinion(){}

    public Opinion(int score, @NotBlank String title, @NotBlank @Size(max = 500) String description, @NotNull @Valid Product product, @NotNull @Valid User opinionator) {
        this.score = score;
        this.title = title;
        this.description = description;
        this.product = product;
        this.opinionator = opinionator;
    }
}
