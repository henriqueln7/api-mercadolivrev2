package com.mercadolivre.apimlv2.usecases.finalizepurchase;

import com.mercadolivre.apimlv2.domain.Purchase;
import com.mercadolivre.apimlv2.shared.mail.Mailer;
import org.springframework.stereotype.Service;

@Service
public class SendMailPurchaseFailure implements EventPurchaseFailure {

    private final Mailer mailer;

    public SendMailPurchaseFailure(Mailer mailer) {
        this.mailer = mailer;
    }

    @Override
    public void execute(Purchase purchase) {
        // Could not inject uriComponentBuilder here to generate the Payment URL properly =(
        mailer.sendText(purchase.getBuyer().getLogin(), "[API MercadoLivre] Payment has failed", "Try again: ");
    }
}
