package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.Product;
import com.mercadolivre.apimlv2.domain.ProductRepository;
import com.mercadolivre.apimlv2.domain.Purchase;
import com.mercadolivre.apimlv2.domain.User;
import com.mercadolivre.apimlv2.security.LoggedUser;
import com.mercadolivre.apimlv2.usecases.addquestiontoproduct.Mailer;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    public String finalizePurchase(@RequestBody @Valid FinalizePurchaseRequest request, @AuthenticationPrincipal LoggedUser loggedUser) {
        /**
         * O sistema registra uma nova compra e gera um identificador de compras que pode ser usado como argumento para o gateway de pagamento.
         * O cliente efetua o pagamento no gateway
         * O gateway invoca uma url do sistema passando o identificador de compra do próprio sistema e as informações relativas a transação em si.
         *
         * Estoque do produto abatido
         * Enviar email para o dono do produto informando que uma compra gostaria de ser feita
         * Compra é gerada com o status INICIADA, contendo as seguintes informações:
         *  Gateway escolhido para pagamento
         *  Produto escolhido
         *  Quantidade
         *  Comprador
         *
         *
         * Caso a pessoa escolha o paypal seu endpoint deve gerar o seguinte redirect(302):
         * Retorne o endereço da seguinte maneira: paypal.com/{idGeradoDaCompra}?redirectUrl={urlRetornoAppPosPagamento}
         * Caso a pessoa escolha o pagseguro o seu endpoint deve gerar o seguinte redirect(302):
         * Retorne o endereço da seguinte maneira: pagseguro.com?returnId={idGeradoDaCompra}&redirectUrl={urlRetornoAppPosPagamento}
         */

        Optional<Product> optionalProduct = productRepository.findById(request.getProductId());
        Product product = optionalProduct.get();
        boolean beatStockSuccessful = product.beatStock(request.getAmount());

        if (beatStockSuccessful) {
            mailer.sendText(product.getOwner().getLogin(), "[MercadoLivre] New purchase :)", "Hi! \n A new purchase is being made on your product " + product.getName());
            User buyer = loggedUser.get();
            Purchase purchase = request.toModel(productRepository, buyer);
            return purchase.toString();
        }

        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No amount available");

    }
}
