package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.Purchase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;

class EventsNewPaymentTransactionTest {

    @Test
    @DisplayName("It should fire successful events if transaction is successful")
    void itShouldFireSuccessfulEventsIfTransactionIsSuccessful() {

        Purchase purchase = PurchaseDataBuilder.newPurchase().successful();

        EventPurchaseSuccessful eventPurchaseSuccessful = Mockito.mock(EventPurchaseSuccessful.class);
        Set<EventPurchaseSuccessful> eventsPurchaseSuccessful = Set.of(eventPurchaseSuccessful);

        EventsNewPaymentTransaction eventsNewPaymentTransaction = new EventsNewPaymentTransaction(eventsPurchaseSuccessful, Set.of());

        eventsNewPaymentTransaction.execute(purchase);

        Mockito.verify(eventPurchaseSuccessful).execute(purchase);
    }

    @Test
    @DisplayName("It should fire failure events if transaction is not successful")
    void itShouldFireFailureEventsIfTransactionIsNotSuccessful() {
        Purchase purchase = PurchaseDataBuilder.newPurchase().notSuccessful();

        EventPurchaseFailure eventPurchaseFailure = Mockito.mock(EventPurchaseFailure.class);
        Set<EventPurchaseFailure> eventsPurchaseFailure = Set.of(eventPurchaseFailure);

        EventsNewPaymentTransaction eventsNewPaymentTransaction = new EventsNewPaymentTransaction(Set.of(), eventsPurchaseFailure);

        eventsNewPaymentTransaction.execute(purchase);

        Mockito.verify(eventPurchaseFailure).execute(purchase);
    }
}