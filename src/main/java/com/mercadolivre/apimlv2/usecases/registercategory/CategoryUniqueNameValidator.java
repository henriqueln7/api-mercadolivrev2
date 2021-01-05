package com.mercadolivre.apimlv2.usecases.registercategory;

import com.mercadolivre.apimlv2.domain.Category;
import com.mercadolivre.apimlv2.domain.CategoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class CategoryUniqueNameValidator implements Validator {

    private final CategoryRepository categoryRepository;

    public CategoryUniqueNameValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewCategoryRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        NewCategoryRequest request = (NewCategoryRequest) target;

        Optional<Category> optionalCategory = categoryRepository.findByName(request.name);

        if (optionalCategory.isPresent()) {
            errors.rejectValue("name", "", "Name already exists");
        }
    }
}
