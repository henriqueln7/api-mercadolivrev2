package com.mercadolivre.apimlv2.domain;

import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Locale;
import java.util.UUID;

public class Purchase {
    private String id;
    private final StatusPurchase statusPurchase;
    private @Positive int amount;
    private @NotNull @Valid User buyer;
    private @NotNull PaymentGateway paymentGateway;
    private @NotNull @Valid Product product;

    public Purchase(@NotNull @Valid Product product, @Positive int amount, @NotNull @Valid User buyer, @NotNull PaymentGateway paymentGateway) {
        this.id = UUID.randomUUID().toString();
        this.product = product;
        this.amount = amount;
        this.buyer = buyer;
        this.paymentGateway = paymentGateway;
        this.statusPurchase = StatusPurchase.STARTED;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "statusPurchase=" + statusPurchase +
                ", amount=" + amount +
                ", buyer=" + buyer +
                ", paymentGateway=" + paymentGateway +
                ", product=" + product +
                '}';
    }

    public String getId() {
        return id;
    }

    public String generatePaymentGatewayUrl(UriComponentsBuilder uriComponentsBuilder) {
        String callbackUrl = uriComponentsBuilder.path("/payment/{purchaseId}")
                                             .queryParam("gateway", this.paymentGateway.name().toLowerCase(Locale.ROOT))
                                             .build(this.getId())
                                             .toString();
        return this.paymentGateway.generatePurchasePaymentGatewayUrl(this, callbackUrl);
    }
}
