package com.mercadolivre.apimlv2.domain;

import com.mercadolivre.apimlv2.usecases.purchase.PaymentTransaction;
import io.jsonwebtoken.lang.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Purchase {
    @Id
    private String id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusPurchase statusPurchase;
    @Positive
    private int amount;
    @Enumerated(EnumType.STRING)
    private @NotNull PaymentGateway paymentGateway;
    @NotNull @Valid
    @ManyToOne
    private User buyer;
    @NotNull @Valid
    @ManyToOne
    private Product product;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "purchase")
    private final List<PaymentTransaction> paymentTransactions = new ArrayList<>();

    @Deprecated
    protected Purchase(){}

    public Purchase(@NotNull @Valid Product product, @Positive int amount, @NotNull @Valid User buyer, @NotNull PaymentGateway paymentGateway) {
        this.id = UUID.randomUUID().toString();
        this.product = product;
        this.amount = amount;
        this.buyer = buyer;
        this.paymentGateway = paymentGateway;
        this.statusPurchase = StatusPurchase.STARTED;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "statusPurchase=" + statusPurchase +
                ", amount=" + amount +
                ", buyer=" + buyer +
                ", paymentGateway=" + paymentGateway +
                ", product=" + product +
                '}';
    }

    public String getId() {
        return id;
    }

    public String generatePaymentGatewayUrl(UriComponentsBuilder uriComponentsBuilder) {
        return this.paymentGateway.generatePurchasePaymentGatewayUrl(this, uriComponentsBuilder);
    }

    public void addPaymentTransaction(@NotNull @Valid PaymentTransaction paymentTransaction) {
        Assert.notNull(paymentTransaction, "Payment attempt is null");
        Assert.isTrue(this.paymentTransactions.stream()
                                              .filter(PaymentTransaction::successful)
                                              .findAny()
                                              .isEmpty(),
                      "This purchase already has a successful payment!");
        Assert.isTrue(!this.paymentTransactions.contains(paymentTransaction), "This payment is already at this purchase");
        this.paymentTransactions.add(paymentTransaction);
    }

    public User getBuyer() {
        return buyer;
    }

    public Product getProduct() {
        return product;
    }

    public List<PaymentTransaction> getPaymentTransactions() {
        return Collections.unmodifiableList(paymentTransactions);
    }

    private Set<PaymentTransaction> successfulPayments() {
        Set<PaymentTransaction> successfulPayments = this.paymentTransactions.stream()
                                                                  .filter(PaymentTransaction::successful)
                                                                  .collect(Collectors.toSet());
        Assert.isTrue(successfulPayments.size() <= 1, "You should not have more than one successful payment at this purchase");
        return successfulPayments;
    }

    public boolean successful() {
        return !this.successfulPayments().isEmpty();
    }
}
