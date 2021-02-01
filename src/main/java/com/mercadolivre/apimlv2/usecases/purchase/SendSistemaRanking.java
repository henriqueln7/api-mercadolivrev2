package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.Purchase;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SendSistemaRanking implements EventsPurchaseSuccessful {
    @Override
    public void execute(Purchase purchase) {
        RestTemplate restTemplate = new RestTemplate();

        SistemaRankingRequest sistemaRankingRequest = new SistemaRankingRequest(purchase.getId(), purchase.getProduct().getOwner().getId());

        restTemplate.postForObject("http://localhost:8080/sistema-ranking", sistemaRankingRequest, String.class);
    }
}
