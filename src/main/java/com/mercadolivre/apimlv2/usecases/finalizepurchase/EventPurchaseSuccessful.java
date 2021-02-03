package com.mercadolivre.apimlv2.usecases.finalizepurchase;

import com.mercadolivre.apimlv2.domain.Purchase;

public interface EventPurchaseSuccessful {
    void execute(Purchase purchase);
}
