package com.mercadolivre.apimlv2.domain;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.Locale;

public enum PaymentGateway {
    PAYPAL("paypal.com/{idGeradoDaCompra}?redirectUrl={urlRetornoAppPosPagamento}"),
    PAGSEGURO("pagseguro.com/?returnId={idGeradoDaCompra}&redirectUrl={urlRetornoAppPosPagamento}");

    /**
     * Gateway url that we need to send the user to finalize the purchase
     */
    private final String redirectUrl;

    PaymentGateway(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String generatePurchasePaymentGatewayUrl(Purchase purchase, UriComponentsBuilder uriComponentsBuilder) {
        String callbackUrl = uriComponentsBuilder.replacePath("/payment/{purchaseId}")
                                                 .queryParam("gateway", this.name().toLowerCase(Locale.ROOT))
                                                 .build(purchase.getId())
                                                 .toString();
        return UriComponentsBuilder.fromUriString(this.redirectUrl)
                            .buildAndExpand(purchase.getId(), callbackUrl)
                            .toString();
    }
}
