package com.mercadolivre.apimlv2.usecases.finalizepurchase;

import com.mercadolivre.apimlv2.domain.*;
import org.springframework.util.Assert;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Optional;

public class FinalizePurchaseRequest {
    @Positive
    private final int amount;
    @NotNull
    private final Long productId;
    @NotNull
    private final PaymentGateway paymentGateway;
    public FinalizePurchaseRequest(int amount, Long productId, @NotNull PaymentGateway paymentGateway) {
        this.amount = amount;
        this.productId = productId;
        this.paymentGateway = paymentGateway;
    }

    public Purchase toModel(ProductRepository productRepository, @NotNull @Valid User buyer) {
        Optional<Product> optionalProduct = productRepository.findById(this.productId);
        Assert.isTrue(optionalProduct.isPresent(), "Invalid ID");

        return new Purchase(optionalProduct.get(), this.amount, buyer, this.paymentGateway);
    }

    public Long getProductId() {
        return productId;
    }

    public int getAmount() {
        return this.amount;
    }

    public PaymentGateway getPaymentGateway() {
        return paymentGateway;
    }
}
