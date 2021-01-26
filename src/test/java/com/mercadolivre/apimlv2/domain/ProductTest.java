package com.mercadolivre.apimlv2.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;
    public int amountAvailable;

    @BeforeEach
    void setUp() {
        Set<ProductFeature> features = Set.of(new ProductFeature("key1", "value1"), new ProductFeature("key2", "value2"), new ProductFeature("key3", "value3"));
        amountAvailable = 10;
        product = new Product("Produto", BigDecimal.TEN, amountAvailable, features, "Description", new Category("Category name"), new User("user@email.com", new CleanPassword("abc123")));
    }

    @Nested
    class BeatStock {
        @ParameterizedTest
        @ValueSource(ints = {-1, 0})
        @DisplayName("It should thrown exception if amountToBeat is not positive")
        void itShouldThrownExceptionIfAmountToBeatIsNotPositive(int amountToBeat) {
            assertThrows(IllegalArgumentException.class, () -> product.beatStock(amountToBeat));
        }

        @ParameterizedTest
        @ValueSource(ints = {3, 9, 10})
        @DisplayName("It should return true if product has amount available enough to beat")
        void itShouldReturnTrueIfProductHasAmountAvailableEnoughToBeat(int amountToBeat) {
            assertTrue(product.beatStock(amountToBeat));
            assertEquals(amountAvailable - amountToBeat, product.getAmountAvailable());
        }

        @ParameterizedTest
        @ValueSource(ints = {11, 15})
        @DisplayName("It should return false if product has no amount availabe enough to beat")
        void itShouldReturnFalseIfProductHasNoAmountAvailabeEnoughToBeat(int amountToBeat) {
            assertFalse(product.beatStock(amountToBeat));
            assertEquals(amountAvailable, product.getAmountAvailable());
        }
    }

}