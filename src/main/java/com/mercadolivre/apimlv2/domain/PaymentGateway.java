package com.mercadolivre.apimlv2.domain;

import org.springframework.web.util.UriComponentsBuilder;

public enum PaymentGateway {
    PAYPAL("paypal.com/{idGeradoDaCompra}?redirectUrl={urlRetornoAppPosPagamento}"),
    PAGSEGURO("pagseguro.com/?returnId={idGeradoDaCompra}&redirectUrl={urlRetornoAppPosPagamento}");

    private final String redirectUrl;

    PaymentGateway(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String generatePurchasePaymentGatewayUrl(Purchase purchase, String callbackUrl) {
        return UriComponentsBuilder.fromUriString(this.redirectUrl)
                            .buildAndExpand(purchase.getId(), callbackUrl)
                            .toString();
    }
}
