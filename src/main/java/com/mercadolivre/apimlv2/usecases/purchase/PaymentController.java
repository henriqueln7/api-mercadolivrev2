package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.Purchase;
import com.mercadolivre.apimlv2.usecases.addquestiontoproduct.Mailer;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class PaymentController {

    private final Mailer mailer;
    @PersistenceContext
    private EntityManager manager;

    public PaymentController(Mailer mailer) {
        this.mailer = mailer;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(new PaymentUniqueGatewayId(manager));
    }

    @PostMapping("/pagseguro-callback/{purchaseId}")
    @Transactional
    public String payment(@PathVariable String purchaseId, @RequestBody @Valid PagseguroPaymentRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Purchase purchase = manager.find(Purchase.class, purchaseId);
        PaymentTransaction paymentTransaction = request.toModel(purchase);
        purchase.addPaymentAttempt(paymentTransaction);
        manager.merge(purchase);
        if (paymentTransaction.successful()) {
            RestTemplate restTemplate = new RestTemplate();

            NotaFiscalRequest notaFiscalRequest = new NotaFiscalRequest(purchase.getId(), purchase.getBuyer().getId());
            SistemaRankingRequest sistemaRankingRequest = new SistemaRankingRequest(purchase.getId(), purchase.getProduct().getOwner().getId());

            restTemplate.postForObject("http://localhost:8080/nota-fiscal", notaFiscalRequest, String.class);
            restTemplate.postForObject("http://localhost:8080/sistema-ranking", sistemaRankingRequest, String.class);
        } else {
            mailer.sendText(purchase.getBuyer().getLogin(), "[API MercadoLivre] Payment has failed", "Try again: " + purchase.generatePaymentGatewayUrl(uriComponentsBuilder));
        }

        return paymentTransaction.toString();
    }
}
