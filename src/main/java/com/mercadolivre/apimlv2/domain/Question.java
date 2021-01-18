package com.mercadolivre.apimlv2.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;
    @NotNull @Valid
    @ManyToOne
    private User questioner;
    @NotNull @Valid
    @ManyToOne
    private Product product;
    private LocalDateTime createdAt;

    @Deprecated
    protected Question(){}
    public Question(@NotBlank String title, @NotNull @Valid User questioner, @NotNull @Valid Product product) {
        this.title = title;
        this.questioner = questioner;
        this.product = product;
        this.createdAt = LocalDateTime.now();
    }
}
