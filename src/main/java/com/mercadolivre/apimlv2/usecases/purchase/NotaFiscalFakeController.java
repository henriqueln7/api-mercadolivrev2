package com.mercadolivre.apimlv2.usecases.purchase;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class NotaFiscalFakeController {

    @PostMapping("/nota-fiscal")
    public String notaFiscal(@RequestBody @Valid NotaFiscalRequest request) {
        return request.toString();
    }

}
