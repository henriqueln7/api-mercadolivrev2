package com.mercadolivre.apimlv2.usecases.registercategory;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CategoryUniqueNameValidator implements Validator {

    private final EntityManager manager;

    public CategoryUniqueNameValidator(EntityManager manager) {
        this.manager = manager;
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
        Query query = manager.createQuery("SELECT 1 FROM Category c WHERE c.name = :name")
                            .setParameter("name", request.name);
        List<?> result = query.getResultList();
        if (!result.isEmpty()) {
            errors.rejectValue("name", "", "Name already exists");
        }
    }
}
