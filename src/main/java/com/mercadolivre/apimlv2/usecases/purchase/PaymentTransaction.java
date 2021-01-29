package com.mercadolivre.apimlv2.usecases.purchase;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String gatewayPaymentId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentTransactionStatus status;
    private LocalDateTime createdAt;

    @Deprecated
    protected PaymentTransaction(){}

    public PaymentTransaction(@NotBlank String gatewayPaymentId, @NotNull PaymentTransactionStatus status) {
        this.gatewayPaymentId = gatewayPaymentId;
        this.createdAt = LocalDateTime.now();
        this.status = status;
    }

    @Override
    public String toString() {
        return "PaymentTransaction{" + "id=" + id + ", paymentAttemptId='" + gatewayPaymentId + '\'' + ", status=" + status + ", createdAt=" + createdAt + '}';
    }

    public boolean successful() {
        return this.status.equals(PaymentTransactionStatus.SUCCESS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentTransaction that = (PaymentTransaction) o;
        return Objects.equals(gatewayPaymentId, that.gatewayPaymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gatewayPaymentId);
    }
}
