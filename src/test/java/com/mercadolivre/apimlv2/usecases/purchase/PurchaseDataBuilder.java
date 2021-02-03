package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.*;

import java.math.BigDecimal;
import java.util.Set;

public class PurchaseDataBuilder {

    public static CompraFixture newPurchase() {
        Set<ProductFeature> features = Set.of(new ProductFeature("key1", "value2"), new ProductFeature("key2", "value2"), new ProductFeature("key3", "value3"));

        Product product = new Product("Product name", BigDecimal.TEN, 10, features, "Description", new Category("Name"), new User("user@email.com", new CleanPassword("abc123")));

        Purchase purchase = new Purchase(product, 2, new User("buyer@email.com", new CleanPassword("abc123")), PaymentGateway.PAGSEGURO);

        return new CompraFixture(purchase);
    }

    public static class CompraFixture {
        private Purchase purchase;

        public CompraFixture(Purchase purchase) {
            this.purchase = purchase;
        }

        public Purchase successful() {
            PaymentTransaction paymentTransaction = new PaymentTransaction("1", PaymentTransactionStatus.SUCCESS, this.purchase);
            this.purchase.addPaymentTransaction(paymentTransaction);

            return this.purchase;
        }

        public Purchase notSuccessful() {
            PaymentTransaction paymentTransaction = new PaymentTransaction("1", PaymentTransactionStatus.ERROR, this.purchase);
            this.purchase.addPaymentTransaction(paymentTransaction);

            return this.purchase;
        }
    }
}
