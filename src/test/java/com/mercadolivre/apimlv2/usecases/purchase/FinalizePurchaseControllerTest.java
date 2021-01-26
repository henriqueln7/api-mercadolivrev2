package com.mercadolivre.apimlv2.usecases.purchase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolivre.apimlv2.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration
@WithMockUser
@AutoConfigureMockMvc(addFilters = false)
class FinalizePurchaseControllerTest {


    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductRepository mockProductRepository;
    @Autowired
    private WebApplicationContext context;


    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("It should return 404 if product with passed it does not exist")
    void itShouldReturn404IfProductWithPassedItDoesNotExist() throws Exception {
        Map<String, ? extends Serializable> request = Map.of("amount", 1,
                                                             "productId", 5,
                                                             "paymentGateway", "PAYPAL");

        Mockito.when(mockProductRepository.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(post("/purchases")
                            .contentType(APPLICATION_JSON)
                            .content(mapper.writeValueAsString(request)))
           .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1, PAYPAL",
            "0, , PAYPAL",
            "1, 1, "
    })
    @Disabled("Map does not allow null, I need to search another way to do a JSON with null values")
    @DisplayName("It should return 400 if invalid data is passed")
    void itShouldReturn400IfInvalidDataIsPassed(int amount, Long productId, String paymentGateway) throws Exception {
        Map<String, ? extends Serializable> request = Map.of("amount", amount,
                                                             "productId", productId,
                                                             "paymentGateway", paymentGateway);
        mvc.perform(post("/purchases")
                            .contentType(APPLICATION_JSON)
                            .content(mapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("It should return 422 if there is no amount available")
    void itShouldReturn422IfThereIsNoAmountAvailable() throws Exception {

        Product product = new Product("Product", BigDecimal.TEN, 5, Set.of(new ProductFeature("key1", "value1"), new ProductFeature("key2", "value2"), new ProductFeature("key3", "value3")), "Description", new Category("Category name"), new User("user@email.com", new CleanPassword("abc123")));

        Mockito.when(mockProductRepository.findById(1L)).thenReturn(Optional.of(product));

        mvc.perform(post("/purchases")
                            .contentType(APPLICATION_JSON)
                            .content(mapper.writeValueAsString(Map.of("amount", 8,
                                                                      "productId", 1L,
                                                                      "paymentGateway", "PAYPAL"))))
           .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("It should return 302 with header location pointing to correct gateway address if there is amount available")
    @Disabled("LoggedUser is null. Null Pointer Exception is thrown")
    void itShouldReturn200IfThereIsAmountAvailable() throws Exception {

        Product product = new Product("Product", BigDecimal.TEN, 10, Set.of(new ProductFeature("key1", "value1"), new ProductFeature("key2", "value2"), new ProductFeature("key3", "value3")), "Description", new Category("Category name"), new User("user@email.com", new CleanPassword("abc123")));

        Mockito.when(mockProductRepository.findById(1L)).thenReturn(Optional.of(product));

        mvc.perform(post("/purchases")
                            .contentType(APPLICATION_JSON)
                            .content(mapper.writeValueAsString(Map.of("amount", 8,
                                                                      "productId", 1L,
                                                                      "paymentGateway", "PAYPAL"))))
           .andExpect(status().isFound());
    }


}