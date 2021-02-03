package com.mercadolivre.apimlv2.usecases.finalizepurchase;

import com.mercadolivre.apimlv2.domain.Purchase;

public interface EventPurchaseFailure {
    void execute(Purchase purchase);
}
