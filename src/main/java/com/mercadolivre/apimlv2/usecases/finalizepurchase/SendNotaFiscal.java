package com.mercadolivre.apimlv2.usecases.finalizepurchase;

import com.mercadolivre.apimlv2.domain.Purchase;
import com.mercadolivre.apimlv2.shared.fakesystems.NotaFiscalRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SendNotaFiscal implements EventPurchaseSuccessful {
    @Override
    public void execute(Purchase purchase) {
        RestTemplate restTemplate = new RestTemplate();

        NotaFiscalRequest notaFiscalRequest = new NotaFiscalRequest(purchase.getId(), purchase.getBuyer().getId());

        restTemplate.postForObject("http://localhost:8080/nota-fiscal", notaFiscalRequest, String.class);
    }
}
