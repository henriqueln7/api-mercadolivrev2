package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.Purchase;
import com.mercadolivre.apimlv2.usecases.addquestiontoproduct.Mailer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
public class PaymentController {

    private final Mailer mailer;
    @PersistenceContext
    private EntityManager manager;

    public PaymentController(Mailer mailer) {
        this.mailer = mailer;
    }

    @PostMapping("/payments/{purchaseId}")
    public String payment(@PathVariable String purchaseId, @RequestBody @Valid PagseguroPaymentRequest request, UriComponentsBuilder uriComponentsBuilder) {
        /**
         *  RESTRIÇÔES:
         *  O id de uma transação que vem de alguma plataforma de pagamento só pode ser processado com sucesso uma vez.
         *  A transação da plataforma(qualquer que seja) de id X para uma compra Y só pode ser processada com sucesso uma vez.
         *  Uma transação que foi concluída com sucesso não pode ter seu status alterado para qualquer coisa outra coisa.
         *  Não podemos ter duas transações com mesmo id de plataforma externa associada a uma compra.
         */
        Purchase purchase = manager.find(Purchase.class, purchaseId);
        PaymentTransaction paymentTransaction = new PaymentTransaction(request.gatewayPaymentId, request.status);
        purchase.addPaymentAttempt(paymentTransaction);

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
