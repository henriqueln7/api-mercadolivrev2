package com.mercadolivre.apimlv2.usecases.registercategory;

import com.mercadolivre.apimlv2.domain.Category;
import com.mercadolivre.apimlv2.domain.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryUniqueNameValidatorTest {

    @Test
    @DisplayName("It should reject category name if name is not unique")
    void itShouldRejectCategoryNameIfNameIsNotUnique() {
        NewCategoryRequest request = new NewCategoryRequest("Category", null);

        CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
        CategoryUniqueNameValidator validator = new CategoryUniqueNameValidator(categoryRepository);
        Errors errors = new BeanPropertyBindingResult(request, "test");

        Mockito.when(categoryRepository.findByName("Category")).thenReturn(Optional.of(new Category("Category")));

        validator.validate(request, errors);

        assertTrue(errors.hasFieldErrors("name"));
    }

    @Test
    @DisplayName("It should not reject category name if name is unique")
    void itShouldNotRejectCategoryNameIfNameIsUnique() {
        NewCategoryRequest request = new NewCategoryRequest("Category", null);

        CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
        CategoryUniqueNameValidator validator = new CategoryUniqueNameValidator(categoryRepository);
        Errors errors = new BeanPropertyBindingResult(request, "test");

        Mockito.when(categoryRepository.findByName("Category")).thenReturn(Optional.empty());

        validator.validate(request, errors);

        assertFalse(errors.hasFieldErrors("name"));
    }
}