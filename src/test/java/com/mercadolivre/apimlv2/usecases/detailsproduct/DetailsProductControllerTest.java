package com.mercadolivre.apimlv2.usecases.detailsproduct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolivre.apimlv2.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class DetailsProductControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductRepository productRepository;

    @Test
    @DisplayName("It should return status 404 if product with passed it does not exist")
    void itShouldReturnStatus404IfProductWithPassedItDoesNotExist() throws Exception {

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(get("/products/{id}", 1L))
               .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("It should return status 200 if product with passed it exists")
    void itShouldReturnStatus200IfProductWithPassedItExists() throws Exception {
        Product product = new Product("Product name", BigDecimal.TEN, 3, Set.of(new ProductFeature("key1", "description1"), new ProductFeature("key2", "description2"), new ProductFeature("key3", "description3")), "Product description", new Category("Category name"), new User("henrique@gmail.com", new CleanPassword("abc123")));

        // gambiarra para setar o ID do produto =)
        ReflectionTestUtils.setField(product, "id", 1L);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        mvc.perform(get("/products/{id}", 1L))
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("It should return a DetailsProductResponse")
    void itShouldReturnADetailsProductResponse() throws Exception {
        Product product = new Product("Product name", BigDecimal.TEN, 3, Set.of(new ProductFeature("key1", "description1"), new ProductFeature("key2", "description2"), new ProductFeature("key3", "description3")), "Product description", new Category("Category name"), new User("henrique@gmail.com", new CleanPassword("abc123")));

        ReflectionTestUtils.setField(product, "id", 1L);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        DetailProductResponse response = new DetailProductResponse(product);

        MvcResult mvcResult = mvc.perform(get("/products/{id}", 1L))
                                 .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = mapper.writeValueAsString(response);

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);

    }

}