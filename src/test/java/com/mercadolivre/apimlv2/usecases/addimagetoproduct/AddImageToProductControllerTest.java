package com.mercadolivre.apimlv2.usecases.addimagetoproduct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolivre.apimlv2.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AddImageToProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddRepository addRepository;

    @MockBean
    private EntityManager entityManager;

//    @Autowired
//    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithMockUser
    @DisplayName("It should return status 404 if product with passed Id does not exist")
    void itShouldReturnStatus404IfProductWithPassedIdDoesNotExist() throws Exception {

        EntityManager entityManager = Mockito.mock(EntityManager.class);
        Mockito.when(entityManager.find(Product.class, 20L)).thenReturn(null);

        MockMultipartFile file = new MockMultipartFile("images", "ad", MediaType.IMAGE_PNG_VALUE, new byte[1]);

        mvc.perform(multipart("/products/{id}/images", 20L)
                .file(file))
           .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("It should return status 403 if logged user is not the owner of the product")
    void itShouldReturnStatus403IfLoggedUserIsNotTheOwnerOfTheProduct() throws Exception {

        Set<ProductFeature> features = Set.of(new ProductFeature("key1", "value1"), new ProductFeature("key2", "value2"), new ProductFeature("key3", "value3"));
        Category category = new Category("Category");
        User owner = new User("llll@email.com", new CleanPassword("abc123"));

        Product product = new Product("Product", BigDecimal.TEN, 3, features, "Description", category, owner);

//         entityManager = Mockito.mock(EntityManager.class);

        long productId = 1L;
//        Mockito.when(entityManager.find(Mockito.any(), Mockito.any())).thenReturn(product);
        Mockito.when(addRepository.find(Mockito.anyLong())).thenReturn(product);

        MockMultipartFile file = new MockMultipartFile("images", "ad", MediaType.IMAGE_PNG_VALUE, new byte[1]);

        mvc.perform(multipart("/products/{id}/images", productId)
                            .file(file))
           .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user@email.com")
    @DisplayName("It should return 200 if product exists and logged user owners it")
    void itShouldReturn200IfProductExistsAndLoggedUserOwnersIt() throws Exception {
        Set<ProductFeature> features = Set.of(new ProductFeature("key1", "value1"), new ProductFeature("key2", "value2"), new ProductFeature("key3", "value3"));
        Category category = new Category("Category");
        User owner = new User("user@email.com", new CleanPassword("abc123"));

        Product product = new Product("Product", BigDecimal.TEN, 3, features, "Description", category, owner);

        EntityManager entityManager = Mockito.mock(EntityManager.class);
        Mockito.when(entityManager.find(Product.class, 1L)).thenReturn(product);

        MockMultipartFile file = new MockMultipartFile("images", "ad", MediaType.IMAGE_PNG_VALUE, new byte[1]);

        mvc.perform(multipart("/products/{id}/images", 1L)
                            .file(file))
           .andExpect(status().isOk());

        Object images = ReflectionTestUtils.getField(product, "images");
        Assertions.assertThat(images).asList().isNotEmpty();

    }

}