package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.Purchase;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PaypalPaymentRequest implements PaymentRequest {
    @NotBlank
    public final String gatewayPaymentId;
    @Min(0)
    @Max(1)
    public final int status;

    public PaypalPaymentRequest(@NotBlank String gatewayPaymentId, @Min(0) @Max(1) int status) {
        this.gatewayPaymentId = gatewayPaymentId;
        this.status = status;
    }

    @Override
    public PaymentTransaction newPaymentTransaction(@NotNull @Valid Purchase purchase) {
        PaymentTransactionStatus paymentStatus = this.status == 1? PaymentTransactionStatus.SUCCESS : PaymentTransactionStatus.ERROR;
        return new PaymentTransaction(this.gatewayPaymentId, paymentStatus, purchase);
    }

    @Override
    public String getGatewayPaymentId() {
        return this.gatewayPaymentId;
    }
}
