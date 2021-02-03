package com.mercadolivre.apimlv2.shared.fakesystems;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class NotaFiscalFakeController {

    @PostMapping("/nota-fiscal")
    public String notaFiscal(@RequestBody @Valid NotaFiscalRequest request) {
        System.out.println(request.toString());
        return request.toString();
    }

}
