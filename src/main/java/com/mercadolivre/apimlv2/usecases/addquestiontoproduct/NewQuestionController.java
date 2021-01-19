package com.mercadolivre.apimlv2.usecases.addquestiontoproduct;

import com.mercadolivre.apimlv2.domain.Product;
import com.mercadolivre.apimlv2.domain.Question;
import com.mercadolivre.apimlv2.domain.User;
import com.mercadolivre.apimlv2.security.LoggedUser;
import org.springframework.http.HttpStatus;
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

@RestController
public class NewQuestionController {

    @PersistenceContext
    private EntityManager manager;

    private final SendMailNewQuestion sendMailNewQuestion;

    public NewQuestionController(SendMailNewQuestion sendMailNewQuestion) {
        this.sendMailNewQuestion = sendMailNewQuestion;
    }

    @PostMapping("/products/{productId}/questions")
    @Transactional
    public void newQuestion(@PathVariable("productId") Long productId, @AuthenticationPrincipal LoggedUser loggedUser, @Valid @RequestBody NewQuestionRequest request) {
        Product product = manager.find(Product.class, productId);

        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with passed ID does not exists");
        }

        User questioner = loggedUser.get();
        Question newQuestion = request.toModel(questioner, product);

        manager.persist(newQuestion);
        sendMailNewQuestion.send(newQuestion);
    }
}
