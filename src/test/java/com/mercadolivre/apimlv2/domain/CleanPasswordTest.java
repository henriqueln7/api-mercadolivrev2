package com.mercadolivre.apimlv2.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CleanPasswordTest {

    @ParameterizedTest
    @CsvSource({
            "abc123",
            "abc123456"
    })
    @DisplayName("It should accept password with length >= 6")
    void itShouldAcceptPasswordWithLength6(String password) {
        assertDoesNotThrow(() -> new CleanPassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc12", "ae"})
    @DisplayName("It should thrown exception if try to create a password with size less than 6")
    void itShouldThrownExceptionIfTryToCreateAPasswordWithSizeLessThan6(String password) {
        assertThrows(IllegalArgumentException.class, () -> new CleanPassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "    "})
    @DisplayName("It should not accept a empty password ")
    void itShouldNotAcceptAEmptyPassword(String password) {
        assertThrows(IllegalArgumentException.class, () -> new CleanPassword(password));
    }

}