package com.mercadolivre.apimlv2.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Table(name = "users")
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
        this.password = password.hash();
        this.login = login;
        this.createdAt = LocalDate.now();
    }
}
