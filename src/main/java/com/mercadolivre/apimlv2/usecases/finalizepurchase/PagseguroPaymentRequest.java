package com.mercadolivre.apimlv2.usecases.finalizepurchase;

import com.mercadolivre.apimlv2.domain.PaymentTransaction;
import com.mercadolivre.apimlv2.domain.Purchase;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PagseguroPaymentRequest implements PaymentRequest {
    @NotBlank
    public final String gatewayPaymentId;
    @NotNull
    public final PagseguroReturnStatus status;

    public PagseguroPaymentRequest(@NotBlank String gatewayPaymentId, @NotNull PagseguroReturnStatus status) {
        this.gatewayPaymentId = gatewayPaymentId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "PagseguroPaymentRequest{" + "paymentAttemptId='" + gatewayPaymentId + '\'' + ", status=" + status + '}';
    }

    @Override
    public PaymentTransaction newPaymentTransaction(@NotNull @Valid Purchase purchase) {
        return new PaymentTransaction(this.gatewayPaymentId, status.normalize(), purchase);
    }

    /**
     * @return gatewayPaymentId so Jackson can serialize errors properly
     */
    public String getGatewayPaymentId() {
        return gatewayPaymentId;
    }
}

enum PagseguroReturnStatus {
    SUCESSO, ERRO;

    public PaymentTransactionStatus normalize() {
        if (this.equals(SUCESSO)) {
            return PaymentTransactionStatus.SUCCESS;
        }
        return PaymentTransactionStatus.ERROR;
    }
}
