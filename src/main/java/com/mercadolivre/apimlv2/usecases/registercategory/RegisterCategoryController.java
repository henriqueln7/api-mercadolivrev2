package com.mercadolivre.apimlv2.usecases.registercategory;

import com.mercadolivre.apimlv2.domain.Category;
import com.mercadolivre.apimlv2.domain.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class RegisterCategoryController {

    private final CategoryRepository categoryRepository;
    private final CategoryUniqueNameValidator categoryUniqueNameValidator;

    public RegisterCategoryController(CategoryUniqueNameValidator categoryUniqueNameValidator, CategoryRepository categoryRepository) {
        this.categoryUniqueNameValidator = categoryUniqueNameValidator;
        this.categoryRepository = categoryRepository;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(categoryUniqueNameValidator);
    }

    @PostMapping("/categories")
    @Transactional
    public ResponseEntity<Void> registerCategory(@RequestBody @Valid NewCategoryRequest request) {
        Category newCategory = request.toModel(categoryId -> {
            Optional<Category> category = categoryRepository.findById(categoryId);
            if (category.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category ID does not exist");
            }
            return category.get();
        });

        categoryRepository.save(newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
