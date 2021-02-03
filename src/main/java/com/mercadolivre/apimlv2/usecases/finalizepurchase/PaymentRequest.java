package com.mercadolivre.apimlv2.usecases.finalizepurchase;

import com.mercadolivre.apimlv2.domain.PaymentTransaction;
import com.mercadolivre.apimlv2.domain.Purchase;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface PaymentRequest {
    PaymentTransaction newPaymentTransaction(@NotNull @Valid Purchase purchase);

    String getGatewayPaymentId();
}
