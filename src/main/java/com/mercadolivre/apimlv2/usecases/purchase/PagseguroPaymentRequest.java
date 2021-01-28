package com.mercadolivre.apimlv2.usecases.purchase;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PagseguroPaymentRequest {
    @NotBlank
    public final String paymentAttemptId;
    @NotNull
    public final PagseguroReturnStatus status;

    public PagseguroPaymentRequest(@NotBlank String paymentAttemptId, @NotNull PagseguroReturnStatus status) {
        this.paymentAttemptId = paymentAttemptId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "PagseguroPaymentRequest{" + "paymentAttemptId='" + paymentAttemptId + '\'' + ", status=" + status + '}';
    }
}

enum PagseguroReturnStatus {
    SUCESSO, ERRO
}
