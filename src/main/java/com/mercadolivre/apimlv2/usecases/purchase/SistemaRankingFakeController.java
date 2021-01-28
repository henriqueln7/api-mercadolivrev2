package com.mercadolivre.apimlv2.usecases.purchase;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SistemaRankingFakeController {

    @PostMapping("/sistema-ranking")
    public String sistemaRanking(@RequestBody @Valid SistemaRankingRequest request) {
        return request.toString();
    }
}
