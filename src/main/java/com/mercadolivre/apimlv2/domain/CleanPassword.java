package com.mercadolivre.apimlv2.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CleanPassword {
    @NotBlank
    @Size(min = 6)
    private final String password;

    public CleanPassword(@NotBlank @Size(min = 6) String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
