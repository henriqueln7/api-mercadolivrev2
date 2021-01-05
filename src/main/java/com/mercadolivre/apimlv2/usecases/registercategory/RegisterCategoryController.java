package com.mercadolivre.apimlv2.usecases.registercategory;

import com.mercadolivre.apimlv2.domain.Category;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class RegisterCategoryController {

    @PersistenceContext
    private EntityManager manager;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(new CategoryUniqueNameValidator(manager));
    }

    @PostMapping("/categories")
    @Transactional
    public String registerCategory(@RequestBody @Valid NewCategoryRequest request) {
        Category newCategory = request.toModel();
        manager.persist(newCategory);
        return request.toString();
    }
}
