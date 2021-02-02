package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.Purchase;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class PaymentCallbackController {

    @PersistenceContext
    private EntityManager manager;

    private final EventsNewPaymentTransaction eventsNewPaymentTransaction;

    public PaymentCallbackController(EventsNewPaymentTransaction eventsNewPaymentTransaction) {
        this.eventsNewPaymentTransaction = eventsNewPaymentTransaction;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(new PaymentUniqueGatewayId(manager));
    }

    @PostMapping("/pagseguro-callback/{purchaseId}")
    @Transactional
    public void payment(@PathVariable String purchaseId, @RequestBody @Valid PagseguroPaymentRequest request, UriComponentsBuilder uriComponentsBuilder) {
        processPaymentTransaction(purchaseId, request, uriComponentsBuilder);
    }

    @PostMapping("/paypal-callback/{purchaseId}")
    @Transactional
    public void paypal(@PathVariable("purchaseId") String purchaseId, @RequestBody @Valid PaypalPaymentRequest request, UriComponentsBuilder uriComponentsBuilder) {
        processPaymentTransaction(purchaseId, request, uriComponentsBuilder);
    }

    private PaymentTransaction processPaymentTransaction(String purchaseId, PaymentRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Purchase purchase = manager.find(Purchase.class, purchaseId);
        PaymentTransaction paymentTransaction = request.newPaymentTransaction(purchase);
        purchase.addPaymentTransaction(paymentTransaction);
        manager.merge(purchase);
        eventsNewPaymentTransaction.execute(purchase, uriComponentsBuilder);
        return paymentTransaction;
    }

}
