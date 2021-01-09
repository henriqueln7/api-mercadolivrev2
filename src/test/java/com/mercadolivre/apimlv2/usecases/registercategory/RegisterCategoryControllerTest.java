package com.mercadolivre.apimlv2.usecases.registercategory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class RegisterCategoryControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("It should return status 201 if a new Category is created with valid data")
    void itShouldReturnStatus201IfANewCategoryIsCreatedWithValidData() throws Exception {
        NewCategoryRequest request = new NewCategoryRequest("Category", null);
        mvc.perform(post("/categories")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(request)))
           .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "    "})
    @DisplayName("It should return status 400 if a invalid category name is passed")
    void itShouldReturnStatus400IfAInvalidCategoryNameIsPassed(String categoryName) throws Exception {
        NewCategoryRequest request = new NewCategoryRequest(categoryName, null);
        mvc.perform(post("/categories")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());
    }
}