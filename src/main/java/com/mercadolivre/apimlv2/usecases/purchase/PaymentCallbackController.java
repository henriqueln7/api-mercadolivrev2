package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.Purchase;
import com.mercadolivre.apimlv2.usecases.addquestiontoproduct.Mailer;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
public class PaymentCallbackController {

    private final List<EventsPurchaseSuccessful> eventsPurchaseSuccessful;
    private final Mailer mailer;
    @PersistenceContext
    private EntityManager manager;

    public PaymentCallbackController(Mailer mailer, List<EventsPurchaseSuccessful> eventsPurchaseSuccessful) {
        this.mailer = mailer;
        this.eventsPurchaseSuccessful = eventsPurchaseSuccessful;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(new PaymentUniqueGatewayId(manager));
    }

    @PostMapping("/pagseguro-callback/{purchaseId}")
    @Transactional
    public String payment(@PathVariable String purchaseId, @RequestBody @Valid PagseguroPaymentRequest request, UriComponentsBuilder uriComponentsBuilder) {
        PaymentTransaction paymentTransaction = processPaymentTransaction(purchaseId, request, uriComponentsBuilder);

        return paymentTransaction.toString();
    }

    @PostMapping("/paypal-callback/{purchaseId}")
    @Transactional
    public String paypal(@PathVariable("purchaseId") String purchaseId, @RequestBody @Valid PaypalPaymentRequest request, UriComponentsBuilder uriComponentsBuilder) {
        PaymentTransaction paymentTransaction = processPaymentTransaction(purchaseId, request, uriComponentsBuilder);

        return paymentTransaction.toString();
    }

    private PaymentTransaction processPaymentTransaction(String purchaseId, PaymentRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Purchase purchase = manager.find(Purchase.class, purchaseId);
        PaymentTransaction paymentTransaction = request.newPaymentTransaction(purchase);
        purchase.addPaymentTransaction(paymentTransaction);
        manager.merge(purchase);
        if (paymentTransaction.successful()) {
            eventsPurchaseSuccessful.forEach(event -> event.execute(purchase));
        } else {
            mailer.sendText(purchase.getBuyer()
                                    .getLogin(), "[API MercadoLivre] Payment has failed", "Try again: " + purchase.generatePaymentGatewayUrl(uriComponentsBuilder));
        }
        return paymentTransaction;
    }

}
