package com.mercadolivre.apimlv2.usecases.purchase;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

public class PaymentUniqueGatewayId implements Validator {

    private final EntityManager manager;

    public PaymentUniqueGatewayId(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PaymentRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        PaymentRequest request = (PaymentRequest) target;
        String gatewayPaymentId = request.getGatewayPaymentId();
        List<PaymentTransaction> resultStream = manager.createQuery("SELECT p FROM PaymentTransaction p WHERE p.gatewayPaymentId = :gateway", PaymentTransaction.class)
                                                       .setParameter("gateway", gatewayPaymentId)
                                                       .getResultList();
        if (!resultStream.isEmpty()) {
            errors.rejectValue("gatewayPaymentId", null, "This payment was already processed. Bad request.");
        }
    }
}
