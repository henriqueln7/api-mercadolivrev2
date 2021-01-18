package com.mercadolivre.apimlv2.usecases.addimagetoproduct;

import com.mercadolivre.apimlv2.domain.Product;
import com.mercadolivre.apimlv2.domain.User;
import com.mercadolivre.apimlv2.security.LoggedUser;
import com.mercadolivre.apimlv2.shared.imageupload.ImageUploader;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@RestController
public class AddImageToProductController {

    @PersistenceContext
    private EntityManager manager;

    private final ImageUploader imageUploader;

    public AddImageToProductController(ImageUploader imageUploader) {
        this.imageUploader = imageUploader;
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

        Set<String> imagesUrl = imageUploader.upload(request.getImages());

        product.addImagesUrl(imagesUrl);
        manager.merge(product);

    }
}