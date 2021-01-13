package com.mercadolivre.apimlv2.usecases.registerproduct;

import com.mercadolivre.apimlv2.domain.Product;
import com.mercadolivre.apimlv2.domain.User;
import com.mercadolivre.apimlv2.security.LoggedUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@RestController
public class RegisterProductController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/products")
    @Transactional
    public ResponseEntity<RegisterProductResponse> registerProduct(@RequestBody @Valid RegisterProductRequest request, @AuthenticationPrincipal LoggedUser loggedUser) {
        User productOwner = loggedUser.get();
        Product newProduct = request.toModel(productOwner, manager);
        manager.persist(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterProductResponse(newProduct));
    }

    @PostMapping("/products/{productId}/images")
    @Transactional
    public void addImages(@PathVariable Long productId,
                            @Valid AddImagesProductRequest request,
                            @AuthenticationPrincipal LoggedUser loggedUser) {
        Product product = manager.find(Product.class, productId);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exists");
        }

        User user = loggedUser.get();

        if (!product.belongsTo(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this operation");
        }

        ImageUploader uploader = new ImageUploader();
        Set<String> imagesUrl = uploader.upload(request.getImages());

        product.addImagesUrl(imagesUrl);
        manager.merge(product);

    }
}
