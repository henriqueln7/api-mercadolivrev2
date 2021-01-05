package com.mercadolivre.apimlv2.usecases.registeruser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterUserControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("It should return status 201 if all parameters are valid")
    void itShouldReturnStatus201IfAllParametersAreValid() throws Exception {

        RegisterUserRequest request = new RegisterUserRequest("henrique@gmail.com", "abc123");

        mvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(request)))
           .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @CsvSource({
            ", abc123",
            "henrique, abc123",
            "henrique@gmail.com, ",
            "henrique@gmail.com, abc12"
    })
    @DisplayName("It should return status 400 if some parameter is invalid {0} ")
    void itShouldReturnStatus400IfSomeParameterIsInvalid(String login, String password) throws Exception {
        RegisterUserRequest request = new RegisterUserRequest(login, password);
        mvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());

    }
}