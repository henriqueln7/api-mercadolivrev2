package com.mercadolivre.apimlv2.usecases.registercategory;

import com.mercadolivre.apimlv2.domain.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class NewCategoryRequestTest {

    @Test
    @DisplayName("It should create a category with categoryParent if categoryParentId is passed")
    void itShouldCreateACategoryWithCategoryParentIfCategoryParentIdIsPassed() {
        NewCategoryRequest request = new NewCategoryRequest("Category", 1L);
        Category category = request.toModel((id) -> new Category("Category parent"));
        Category parentCategory = (Category) ReflectionTestUtils.getField(category, "parentCategory");
        assertNotNull(parentCategory);
    }

    @Test
    @DisplayName("It should not create a category with categoryParent if categoryParentId is not passed")
    void itShouldNotCreateACategoryWithCategoryParentIfCategoryParentIdIsNotPassed() {
        NewCategoryRequest request = new NewCategoryRequest("Category", null);
        Category category = request.toModel((id) -> new Category("blablabla"));
        Category parentCategory = (Category) ReflectionTestUtils.getField(category, "parentCategory");
        assertNull(parentCategory);
    }

}