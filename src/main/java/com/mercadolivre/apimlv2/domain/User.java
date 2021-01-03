package com.mercadolivre.apimlv2.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Email
    private String login;
    @NotBlank
    @Size(min = 6)
    private String password;
    @NotNull
    @PastOrPresent
    private LocalDate createdAt;

    @Deprecated
    protected User(){}

    public User(@NotBlank @Email String login, CleanPassword password) {
        this.password = new BCryptPasswordEncoder().encode(password.getPassword());
        this.login = login;
        this.createdAt = LocalDate.now();
    }
}
