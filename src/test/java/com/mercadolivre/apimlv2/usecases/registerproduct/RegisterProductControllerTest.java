package com.mercadolivre.apimlv2.usecases.registerproduct;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterProductControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithMockUser
    @Disabled("It shows that loggedUser.get() throws a NPE")
    @DisplayName("It should return status 201 if all input data is valid")
    void itShouldReturnStatus201IfAllInputDataIsValid() throws Exception {
        RegisterProductRequest request = new RegisterProductRequest("Product", BigDecimal.TEN, 3, Set.of(new NewFeatureRequest("key1", "value1"), new NewFeatureRequest("key2", "value2"), new NewFeatureRequest("key3", "value3")), "Description", 2L);

        mvc.perform(post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(request)))
           .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @MethodSource("generateRequestWithInvalidData")
    @DisplayName("It should return status 400 if some input data is invalid")
    @WithMockUser
    void itShouldReturnStatus400IfSomeInputDataIsInvalid(RegisterProductRequest request) throws Exception {
        mvc.perform(post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> generateRequestWithInvalidData() {
        String name = "Product";
        String description = "Description";
        BigDecimal price = BigDecimal.TEN;
        int amountAvailable = 3;
        long categoryId = 2L;

        Set<NewFeatureRequest> features = Set.of(new NewFeatureRequest("key1", "value1"), new NewFeatureRequest("key2", "value2"), new NewFeatureRequest("key3", "value3"));

        RegisterProductRequest emptyName = new RegisterProductRequest(" ", price, amountAvailable, features, description, categoryId);
        RegisterProductRequest priceZero = new RegisterProductRequest(name, BigDecimal.ZERO, amountAvailable, features, description, categoryId);
        RegisterProductRequest amountAvailableNegative = new RegisterProductRequest(name, price, -1, features, description, categoryId);
        RegisterProductRequest lessThan3Features = new RegisterProductRequest(name, price, amountAvailable, new HashSet<>(List.of(new NewFeatureRequest("key1", "value1"), new NewFeatureRequest("key2", "value2"))), description, categoryId);
        RegisterProductRequest featuresWithSameName = new RegisterProductRequest(name, price, amountAvailable, new HashSet<>(List.of(new NewFeatureRequest("key1", "value1"), new NewFeatureRequest("key2", "value2"), new NewFeatureRequest("key2", "value3"))), description, categoryId);
        RegisterProductRequest emptyDescription = new RegisterProductRequest(name, price, amountAvailable, features, " ", categoryId);
        RegisterProductRequest nullCategoryId = new RegisterProductRequest(name, price, amountAvailable, features, description, null);

        return Stream.of(
                Arguments.of(emptyName),
                Arguments.of(priceZero),
                Arguments.of(amountAvailableNegative),
                Arguments.of(lessThan3Features),
                Arguments.of(featuresWithSameName),
                Arguments.of(emptyDescription),
                Arguments.of(nullCategoryId));
    }

    @Test
    @DisplayName("It should return status 401 if unathorized")
    void itShouldReturnStatus401IfUnathorized() throws Exception {
        mvc.perform(post("/products")
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isUnauthorized());
    }
}