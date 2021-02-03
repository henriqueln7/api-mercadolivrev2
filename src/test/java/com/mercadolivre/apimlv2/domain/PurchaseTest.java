package com.mercadolivre.apimlv2.domain;

import com.mercadolivre.apimlv2.usecases.finalizepurchase.PaymentTransactionStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
            assertThrows(IllegalArgumentException.class, () -> purchase.addPaymentTransaction(null));
        }

        @Test
        @DisplayName("It should thrown exception if purchase already has a successful payment")
        void itShouldThrownExceptionIfPurchaseAlreadyHasASuccessfulPayment() {
            purchase.addPaymentTransaction(new PaymentTransaction("abc123", PaymentTransactionStatus.SUCCESS, purchase));
            assertThrows(IllegalArgumentException.class, () -> purchase.addPaymentTransaction(new PaymentTransaction("abcaaaa", PaymentTransactionStatus.ERROR, purchase)));
        }

        @Test
        @DisplayName("It should thrown exception if purchase already has the same payment passed as parameter")
        void itShouldThrownExceptionIfPurchaseAlreadyHasTheSamePaymentPassedAsParameter() {
            purchase.addPaymentTransaction(new PaymentTransaction("abc123", PaymentTransactionStatus.ERROR, purchase));
            assertThrows(IllegalArgumentException.class, () -> purchase.addPaymentTransaction(new PaymentTransaction("abc123", PaymentTransactionStatus.SUCCESS, purchase)));
        }

        @Test
        @DisplayName("It should add paymentTransaction if is not null and the purchase has not successful payment yet")
        void itShouldAddPaymentTransactionIfIsNotNullAndThePurchaseHasNotSuccessfulPaymentYet() {
            PaymentTransaction paymentTransaction = new PaymentTransaction("abc123",
                                                                           PaymentTransactionStatus.ERROR, purchase);
            purchase.addPaymentTransaction(paymentTransaction);
            Assertions.assertThat(purchase.getPaymentTransactions()).contains(paymentTransaction);
            Assertions.assertThat(purchase.getPaymentTransactions()).hasSize(1);
        }
    }

    @Nested
    class Successful {
        @Test
        @DisplayName("It should be a successful purchase if has a transaction that is successful")
        void itShouldBeASuccessfulPurchaseIfHasATransactionThatIsSuccessful() {
            purchase.addPaymentTransaction(new PaymentTransaction("1", PaymentTransactionStatus.SUCCESS, purchase));

            assertTrue(purchase.successful());
        }

        @Test
        @DisplayName("It should not be a successful purchase if does not have a transaction successful")
        void itShouldNotBeASuccessfulPurchaseIfDoesNotHaveATransactionSuccessful() {
            purchase.addPaymentTransaction(new PaymentTransaction("1", PaymentTransactionStatus.ERROR, purchase));
            assertFalse(purchase.successful());
        }

        @Test
        @DisplayName("It should not be a successful purchase if has no transactions")
        void itShouldNotBeASuccessfulPurchaseIfHasNoTransactions() {
            assertFalse(purchase.successful());
        }
    }


}