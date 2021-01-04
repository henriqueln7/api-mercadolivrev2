package com.mercadolivre.apimlv2.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CleanPassword {
    @NotBlank
    @Size(min = 6)
    private final String password;

    public CleanPassword(@NotBlank @Size(min = 6) String password) {
        Assert.hasText(password, "Password should not be blank");
        Assert.isTrue(password.length() >= 6, "Password should have size >= 6");
        this.password = password;
    }

    public String hash() {
        return new BCryptPasswordEncoder().encode(this.password);
    }
}
