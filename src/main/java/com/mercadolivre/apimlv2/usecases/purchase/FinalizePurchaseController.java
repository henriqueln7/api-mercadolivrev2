package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.*;
import com.mercadolivre.apimlv2.security.LoggedUser;
import com.mercadolivre.apimlv2.usecases.addquestiontoproduct.Mailer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class FinalizePurchaseController {

    private final ProductRepository productRepository;
    private final Mailer mailer;

    public FinalizePurchaseController(ProductRepository productRepository, Mailer mailer) {
        this.productRepository = productRepository;
        this.mailer = mailer;
    }

    @PostMapping("/purchases")
    public ResponseEntity<String> finalizePurchase(@RequestBody @Valid FinalizePurchaseRequest request, @AuthenticationPrincipal LoggedUser loggedUser, UriComponentsBuilder uriComponentsBuilder) {

        Optional<Product> optionalProduct = productRepository.findById(request.getProductId());
        Product product = optionalProduct.get();
        boolean beatStockSuccessful = product.beatStock(request.getAmount());

        if (beatStockSuccessful) {
            mailer.sendText(product.getOwner().getLogin(), "[MercadoLivre] New purchase :)", "Hi! \n A new purchase is being made on your product " + product.getName());
            User buyer = loggedUser.get();
            Purchase purchase = request.toModel(productRepository, buyer);

            if (request.getPaymentGateway().equals(PaymentGateway.PAYPAL)) {
                String paypalUrl = "paypal.com/{idGeradoDaCompra}?redirectUrl={urlRetornoAppPosPagamento}";
                String redirectUrl = uriComponentsBuilder.path("/payment/?gateway=paypal").build().toString();
                String paypalRedirect = UriComponentsBuilder.fromUriString(paypalUrl)
                                               .buildAndExpand(purchase.getId(), redirectUrl)
                                               .toString();

                return ResponseEntity.status(HttpStatus.FOUND).header("Location", paypalRedirect).build();
            } else {
                String pagseguroUrl = "pagseguro.com?returnId={idGeradoDaCompra}&redirectUrl={urlRetornoAppPosPagamento}";
                String redirectUrl = uriComponentsBuilder.path("/payment/?gateway=pagseguro").build().toString();
                String pagseguroRedirect = UriComponentsBuilder.fromPath(pagseguroUrl)
                                                            .buildAndExpand(purchase.getId(), redirectUrl)
                                                            .toString();
                return ResponseEntity.status(HttpStatus.FOUND).header("Location", pagseguroRedirect).build();
            }

        }

        return ResponseEntity.unprocessableEntity().body("No amount available");
    }
}
