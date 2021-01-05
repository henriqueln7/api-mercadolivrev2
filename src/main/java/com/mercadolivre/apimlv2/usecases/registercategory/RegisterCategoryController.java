package com.mercadolivre.apimlv2.usecases.registercategory;

import com.mercadolivre.apimlv2.domain.Category;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class RegisterCategoryController {

    @PersistenceContext
    private EntityManager manager;
    private final CategoryUniqueNameValidator categoryUniqueNameValidator;

    public RegisterCategoryController(CategoryUniqueNameValidator categoryUniqueNameValidator) {
        this.categoryUniqueNameValidator = categoryUniqueNameValidator;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(categoryUniqueNameValidator);
    }

    @PostMapping("/categories")
    @Transactional
    public String registerCategory(@RequestBody @Valid NewCategoryRequest request) {
        Category newCategory = request.toModel(categoryId -> {
            Category category = manager.find(Category.class, categoryId);
            if (category == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category ID does not exist");
            }
            return category;
        });

        manager.persist(newCategory);
        return request.toString();
    }
}
