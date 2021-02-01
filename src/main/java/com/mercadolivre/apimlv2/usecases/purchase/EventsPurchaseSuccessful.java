package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.Purchase;

public interface EventsPurchaseSuccessful {
    void execute(Purchase purchase);
}
