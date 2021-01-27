package com.mercadolivre.apimlv2.usecases.purchase;

import com.mercadolivre.apimlv2.domain.Purchase;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
public class PaymentController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/payments/{purchaseId}")
    public String payment(@PathVariable String purchaseId, @RequestBody @Valid PagseguroPaymentRequest request) {
        /**
         * O meio de pagamento retorna 3 informações para nossa aplicação:
         *  id da compra no sistema de origem
         *  id da compra na plataforma de pagamento
         *  status da compra (SUCESSO, ERRO)
         *      Paypal retorna 1 para sucesso e 0 para erro
         *      Pagseguro retorna a string SUCESSO ou ERRO
         *
         *  -> Registrar as informações de pagamento com todas as tentativas envolvidas
         *  -> Caso a compra tenhas sido concluída com sucesso:
         *      * Comunicar com o setor de nota fiscal, que é outro sistema. Precisa receber o id da compra e o id do usuário que fez a compra.
         *      * Comunicar com o sistema de ranking dos vendedores. Recebe o id da compra e o id do vendedor
         *  -> Caso a compra não possa ser concluída:
         *      * Enviar um email para o usuário informando que o pagamento falhou com o link para que ele possa tentar novamente.
         *
         *  RESTRIÇÔES:
         *  id de compra, id de transação e status são obrigatórios para todas urls de retorno de dentro da aplicação.
         *  O id de uma transação que vem de alguma plataforma de pagamento só pode ser processado com sucesso uma vez.
         *  A transação da plataforma(qualquer que seja) de id X para uma compra Y só pode ser processada com sucesso uma vez.
         *  Uma transação que foi concluída com sucesso não pode ter seu status alterado para qualquer coisa outra coisa.
         *  Não podemos ter duas transações com mesmo id de plataforma externa associada a uma compra.
         */
        Purchase purchase = manager.find(Purchase.class, purchaseId);
        PaymentAttempt paymentAttempt = new PaymentAttempt(request.paymentAttemptId, request.status);
        purchase.addPaymentAttempt(paymentAttempt);

        return paymentAttempt.toString();
    }
}
