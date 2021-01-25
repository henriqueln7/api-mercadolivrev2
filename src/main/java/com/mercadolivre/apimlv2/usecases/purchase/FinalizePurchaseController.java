package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.Product;
import com.mercadolivre.apimlv2.domain.ProductRepository;
import com.mercadolivre.apimlv2.domain.Purchase;
import com.mercadolivre.apimlv2.domain.User;
import com.mercadolivre.apimlv2.security.LoggedUser;
import com.mercadolivre.apimlv2.usecases.addquestiontoproduct.Mailer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class FinalizePurchaseController {

    @PersistenceContext
    private EntityManager manager;

    private final ProductRepository productRepository;
    private final Mailer mailer;

    public FinalizePurchaseController(ProductRepository productRepository, Mailer mailer) {
        this.productRepository = productRepository;
        this.mailer = mailer;
    }

    @PostMapping("/purchases")
    @Transactional
    public ResponseEntity<String> finalizePurchase(@RequestBody @Valid FinalizePurchaseRequest request, @AuthenticationPrincipal LoggedUser loggedUser, UriComponentsBuilder uriComponentsBuilder) {

        Optional<Product> optionalProduct = productRepository.findById(request.getProductId());

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product product = optionalProduct.get();
        boolean beatStockSuccessful = product.beatStock(request.getAmount());

        if (beatStockSuccessful) {

            User buyer = loggedUser.get();
            Purchase purchase = new Purchase(product, request.getAmount(), buyer, request.getPaymentGateway());
            manager.persist(purchase);
            mailer.sendText(product.getOwner().getLogin(), "[MercadoLivre] New purchase :)", "Hi! \n A new purchase is being made on your product " + product.getName());
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(purchase.generatePaymentGatewayUrl(uriComponentsBuilder));
        }
        return ResponseEntity.unprocessableEntity().body("No amount available");
    }
}
