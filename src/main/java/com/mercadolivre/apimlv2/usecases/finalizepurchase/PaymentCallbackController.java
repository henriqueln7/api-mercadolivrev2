package com.mercadolivre.apimlv2.usecases.finalizepurchase;

import com.mercadolivre.apimlv2.domain.PaymentTransaction;
import com.mercadolivre.apimlv2.domain.Purchase;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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
        binder.addValidators(new PaymentUniqueGatewayIdValidator(manager));
    }

    @PostMapping("/pagseguro-callback/{purchaseId}")
    @Transactional
    public void payment(@PathVariable String purchaseId, @RequestBody @Valid PagseguroPaymentRequest request) {
        processPaymentTransaction(purchaseId, request);
    }

    @PostMapping("/paypal-callback/{purchaseId}")
    @Transactional
    public void paypal(@PathVariable("purchaseId") String purchaseId, @RequestBody @Valid PaypalPaymentRequest request) {
        processPaymentTransaction(purchaseId, request);
    }

    private PaymentTransaction processPaymentTransaction(String purchaseId, PaymentRequest request) {
        Purchase purchase = manager.find(Purchase.class, purchaseId);
        PaymentTransaction paymentTransaction = request.newPaymentTransaction(purchase);
        purchase.addPaymentTransaction(paymentTransaction);
        manager.merge(purchase);
        eventsNewPaymentTransaction.execute(purchase);
        return paymentTransaction;
    }

}
