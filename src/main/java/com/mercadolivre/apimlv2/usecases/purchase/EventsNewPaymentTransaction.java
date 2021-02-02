package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.Purchase;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EventsNewPaymentTransaction {

    private final Set<EventPurchaseSuccessful> eventsPurchaseSuccessful;
    private final Set<EventPurchaseFailure> eventsPurchaseFailure;

    public EventsNewPaymentTransaction(Set<EventPurchaseSuccessful> eventsPurchaseSuccessful, Set<EventPurchaseFailure> eventsPurchaseFailure) {
        this.eventsPurchaseSuccessful = eventsPurchaseSuccessful;
        this.eventsPurchaseFailure = eventsPurchaseFailure;
    }

    public void execute(Purchase purchase) {
        if (purchase.successful()) {
            eventsPurchaseSuccessful.forEach(event -> event.execute(purchase));
        } else {
            eventsPurchaseFailure.forEach(event -> event.execute(purchase));
        }
    }
}
