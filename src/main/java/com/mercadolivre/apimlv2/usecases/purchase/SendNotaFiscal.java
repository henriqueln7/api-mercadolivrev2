package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.Purchase;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SendNotaFiscal implements EventsPurchaseSuccessful {
    @Override
    public void execute(Purchase purchase) {
        RestTemplate restTemplate = new RestTemplate();

        NotaFiscalRequest notaFiscalRequest = new NotaFiscalRequest(purchase.getId(), purchase.getBuyer().getId());

        restTemplate.postForObject("http://localhost:8080/nota-fiscal", notaFiscalRequest, String.class);
    }
}
