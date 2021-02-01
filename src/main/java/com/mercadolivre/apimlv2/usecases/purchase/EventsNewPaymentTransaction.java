package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.Purchase;
import com.mercadolivre.apimlv2.usecases.addquestiontoproduct.Mailer;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@Service
public class EventsNewPaymentTransaction {

    private final Set<EventsPurchaseSuccessful> eventsPurchaseSuccessful;
    private final Mailer mailer;

    public EventsNewPaymentTransaction(Set<EventsPurchaseSuccessful> eventsPurchaseSuccessful, Mailer mailer) {
        this.eventsPurchaseSuccessful = eventsPurchaseSuccessful;
        this.mailer = mailer;
    }

    public void execute(Purchase purchase, UriComponentsBuilder uriComponentsBuilder) {
        if (purchase.successful()) {
            eventsPurchaseSuccessful.forEach(event -> event.execute(purchase));
        } else {
            mailer.sendText(purchase.getBuyer().getLogin(), "[API MercadoLivre] Payment has failed", "Try again: " + purchase.generatePaymentGatewayUrl(uriComponentsBuilder));
        }
    }
}
