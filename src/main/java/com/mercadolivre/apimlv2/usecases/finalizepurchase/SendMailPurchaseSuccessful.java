package com.mercadolivre.apimlv2.usecases.finalizepurchase;

import com.mercadolivre.apimlv2.domain.Purchase;
import com.mercadolivre.apimlv2.shared.mail.Mailer;
import org.springframework.stereotype.Service;

@Service
public class SendMailPurchaseSuccessful implements EventPurchaseSuccessful {
    private final Mailer mailer;

    public SendMailPurchaseSuccessful(Mailer mailer) {
        this.mailer = mailer;
    }

    @Override
    public void execute(Purchase purchase) {
        mailer.sendText(purchase.getBuyer().getLogin(), "[API MercadoLivre] Payment successful", "Hi! Your payment of the product " + purchase.getProduct().getName() + " was ok! =)");
    }
}
