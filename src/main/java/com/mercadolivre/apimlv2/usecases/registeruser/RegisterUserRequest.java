package com.mercadolivre.apimlv2.usecases.registeruser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mercadolivre.apimlv2.domain.CleanPassword;
import com.mercadolivre.apimlv2.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterUserRequest {
    @NotBlank
    @Email
    public final String login;
    @NotBlank
    @Size(min = 6)
    public final String password;

    @JsonCreator
    public RegisterUserRequest(@NotBlank @Email String login, @NotBlank @Size(min = 6) String password) {
        this.login = login;
        this.password = password;
    }

    public User toModel() {
        return new User(this.login, new CleanPassword(this.password));
    }
}
