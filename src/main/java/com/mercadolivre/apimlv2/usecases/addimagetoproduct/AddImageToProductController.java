package com.mercadolivre.apimlv2.usecases.addimagetoproduct;

import com.mercadolivre.apimlv2.domain.Product;
import com.mercadolivre.apimlv2.domain.ProductRepository;
import com.mercadolivre.apimlv2.domain.User;
import com.mercadolivre.apimlv2.security.LoggedUser;
import com.mercadolivre.apimlv2.shared.imageupload.ImageUploader;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@RestController
public class AddImageToProductController {

    private final ImageUploader imageUploader;

    private final ProductRepository productRepository;

    public AddImageToProductController(ImageUploader imageUploader, ProductRepository productRepository) {
        this.imageUploader = imageUploader;
        this.productRepository = productRepository;
    }

    @PostMapping("/products/{productId}/images")
    @Transactional
    public void addImages(@PathVariable Long productId,
                          @Valid AddImagesProductRequest request,
                          @AuthenticationPrincipal LoggedUser loggedUser) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exists");
        }

        User user = loggedUser.get();
        Product product = optionalProduct.get();

        if (!product.belongsTo(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this operation");
        }

        Set<String> imagesUrl = imageUploader.upload(request.getImages());

        product.addImagesUrl(imagesUrl);
        productRepository.save(product);
    }
}
