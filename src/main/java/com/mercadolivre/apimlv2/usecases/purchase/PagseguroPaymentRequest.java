package com.mercadolivre.apimlv2.usecases.purchase;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class PagseguroPaymentRequest {
    @NotBlank
    public final String paymentAttemptId;
    @NotNull
    public final PagseguroReturnStatus status;
    private final LocalDateTime createdAt;

    public PagseguroPaymentRequest(@NotBlank String paymentAttemptId, @NotNull PagseguroReturnStatus status) {
        this.paymentAttemptId = paymentAttemptId;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "PagseguroPaymentRequest{" + "paymentAttemptId='" + paymentAttemptId + '\'' + ", status=" + status + '}';
    }
}

enum PagseguroReturnStatus {
    SUCESSO, ERRO
}
