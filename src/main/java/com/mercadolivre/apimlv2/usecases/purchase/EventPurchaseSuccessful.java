package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.Purchase;

public interface EventPurchaseSuccessful {
    void execute(Purchase purchase);
}
