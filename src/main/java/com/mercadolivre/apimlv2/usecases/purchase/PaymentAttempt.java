package com.mercadolivre.apimlv2.usecases.purchase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class PaymentAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String paymentAttemptId;
    @NotNull
    private PaymentAttemptStatus status;

    @Deprecated
    protected PaymentAttempt(){}

    public PaymentAttempt(@NotBlank String paymentAttemptId, @NotNull PagseguroReturnStatus status) {
        this.paymentAttemptId = paymentAttemptId;

        if (status.equals(PagseguroReturnStatus.SUCESSO)) {
            this.status = PaymentAttemptStatus.SUCCESS;
        } else {
            this.status = PaymentAttemptStatus.ERROR;
        }
    }

    @Override
    public String toString() {
        return "PaymentAttempt{" + "paymentAttemptId='" + paymentAttemptId + '\'' + ", status=" + status + '}';
    }
}

enum PaymentAttemptStatus {
    SUCCESS, ERROR
}