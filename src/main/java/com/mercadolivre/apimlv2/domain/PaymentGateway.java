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
        String gatewayName = this.name().toLowerCase(Locale.ROOT);
        String callbackUrl = uriComponentsBuilder.replacePath("/{gatewayName}-callback/{purchaseId}")
                                                 .build(gatewayName, purchase.getId())
                                                 .toString();
        return UriComponentsBuilder.fromUriString(this.redirectUrl)
                            .buildAndExpand(purchase.getId(), callbackUrl)
                            .toString();
    }
}
