package com.mercadolivre.apimlv2.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

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

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    public Long getId() {

        return this.id;
    }
}
