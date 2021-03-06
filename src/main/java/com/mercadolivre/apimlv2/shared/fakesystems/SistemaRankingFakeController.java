package com.mercadolivre.apimlv2.shared.fakesystems;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SistemaRankingFakeController {

    @PostMapping("/sistema-ranking")
    public String sistemaRanking(@RequestBody @Valid SistemaRankingRequest request) {
        System.out.println(request.toString());
        return request.toString();
    }
}
