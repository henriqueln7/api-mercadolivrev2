package com.mercadolivre.apimlv2.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

public class AuthLoginRequest {
    @NotBlank
    private String login;
    @NotBlank
    private String password;

    public AuthLoginRequest(@NotBlank String login, @NotBlank String password) {
        this.login = login;
        this.password = password;
    }

    public UsernamePasswordAuthenticationToken build() {
        return new UsernamePasswordAuthenticationToken(this.login, this.password);
    }
}
