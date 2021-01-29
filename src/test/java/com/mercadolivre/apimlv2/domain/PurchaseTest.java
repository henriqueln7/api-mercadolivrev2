package com.mercadolivre.apimlv2.domain;

import com.mercadolivre.apimlv2.usecases.purchase.PaymentTransaction;
import com.mercadolivre.apimlv2.usecases.purchase.PaymentTransactionStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PurchaseTest {

    private Purchase purchase;

    @BeforeEach
    void setUp() {
        @Size(min = 3) @Valid Set<ProductFeature> features = Set.of(new ProductFeature("key1", "value2"), new ProductFeature("key2", "value2"), new ProductFeature("key3", "value3"));
        Product product = new Product("Product name", BigDecimal.TEN, 10, features, "Description", new Category("Name"), new User("user@email.com", new CleanPassword("abc123")));

        this.purchase = new Purchase(product, 2, new User("buyer@email.com", new CleanPassword("abc123")), PaymentGateway.PAGSEGURO);
    }

    @Nested
    class AddPaymentTransaction {
        @Test
        @DisplayName("It should thrown exception if paymentAttempt is null")
        void itShouldThrownExceptionIfPaymentAttemptIsNull() {
            assertThrows(IllegalArgumentException.class, () -> purchase.addPaymentAttempt(null));
        }

        @Test
        @DisplayName("It should thrown exception if purchase already has a successful payment")
        void itShouldThrownExceptionIfPurchaseAlreadyHasASuccessfulPayment() {
            purchase.addPaymentAttempt(new PaymentTransaction("abc123", PaymentTransactionStatus.SUCCESS));
            assertThrows(IllegalArgumentException.class, () -> purchase.addPaymentAttempt(new PaymentTransaction("abcaaaa", PaymentTransactionStatus.ERROR)));
        }

        @Test
        @DisplayName("It should add paymentTransaction if is not null and the purchase has not successful payment yet")
        void itShouldAddPaymentTransactionIfIsNotNullAndThePurchaseHasNotSuccessfulPaymentYet() {
            PaymentTransaction paymentTransaction = new PaymentTransaction("abc123",
                                                                           PaymentTransactionStatus.ERROR);
            purchase.addPaymentAttempt(paymentTransaction);
            Assertions.assertThat(purchase.getPaymentTransactions()).contains(paymentTransaction);
            Assertions.assertThat(purchase.getPaymentTransactions()).hasSize(1);
        }
    }

}