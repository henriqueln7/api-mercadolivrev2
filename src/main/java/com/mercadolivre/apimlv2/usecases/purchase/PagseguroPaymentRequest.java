package com.mercadolivre.apimlv2.usecases.purchase;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PagseguroPaymentRequest {
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

    public PaymentTransaction toModel() {
        return new PaymentTransaction(this.gatewayPaymentId, status.normalize());
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
