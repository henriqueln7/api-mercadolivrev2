package com.mercadolivre.apimlv2.usecases.registerproduct;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegisterProductController {
    @PostMapping("/products")
    public String registerProduct(@RequestBody @Valid RegisterProductRequest request) {
        return request.toString();
    }
}
