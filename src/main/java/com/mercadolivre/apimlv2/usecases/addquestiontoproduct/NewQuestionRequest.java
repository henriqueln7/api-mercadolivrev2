package com.mercadolivre.apimlv2.usecases.addquestiontoproduct;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolivre.apimlv2.domain.Product;
import com.mercadolivre.apimlv2.domain.Question;
import com.mercadolivre.apimlv2.domain.User;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewQuestionRequest {
    @NotBlank
    public final String title;
    private final String body;

    @JsonCreator
    public NewQuestionRequest(@JsonProperty("title") @NotBlank String title,
                              @JsonProperty("body") @NotBlank String body) {
        this.title = title;
        this.body = body;
    }

    public Question toModel(@NotNull @Valid User questioner, @NotNull @Valid Product product) {
        Question question = new Question(this.title, questioner, product);

        if (StringUtils.hasText(body)) {
            question.setBody(body);
        }

        return question;
    }
}
