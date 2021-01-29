package com.mercadolivre.apimlv2.usecases.purchase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String gatewayPaymentId;
    @NotNull
    private PaymentAttemptStatus status;
    private LocalDateTime createdAt;

    @Deprecated
    protected PaymentTransaction(){}

    public PaymentTransaction(@NotBlank String gatewayPaymentId, @NotNull PagseguroReturnStatus status) {
        this.gatewayPaymentId = gatewayPaymentId;
        this.createdAt = LocalDateTime.now();
        if (status.equals(PagseguroReturnStatus.SUCESSO)) {
            this.status = PaymentAttemptStatus.SUCCESS;
        } else {
            this.status = PaymentAttemptStatus.ERROR;
        }
    }

    @Override
    public String toString() {
        return "PaymentTransaction{" + "id=" + id + ", paymentAttemptId='" + gatewayPaymentId + '\'' + ", status=" + status + ", createdAt=" + createdAt + '}';
    }

    public boolean successful() {
        return this.status.equals(PaymentAttemptStatus.SUCCESS);
    }
}

enum PaymentAttemptStatus {
    SUCCESS, ERROR
}