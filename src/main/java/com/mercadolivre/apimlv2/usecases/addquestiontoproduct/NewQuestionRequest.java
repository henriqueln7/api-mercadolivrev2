package com.mercadolivre.apimlv2.usecases.addquestiontoproduct;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class NewQuestionRequest {
    @NotBlank
    public final String title;

    @JsonCreator
    public NewQuestionRequest(@JsonProperty("title") @NotBlank String title) {
        this.title = title;
    }
}
