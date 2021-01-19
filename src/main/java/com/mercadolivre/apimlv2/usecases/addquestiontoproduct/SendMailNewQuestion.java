package com.mercadolivre.apimlv2.usecases.addquestiontoproduct;

import com.mercadolivre.apimlv2.domain.Question;
import com.mercadolivre.apimlv2.domain.User;
import org.springframework.stereotype.Service;

@Service
public class SendMailNewQuestion {

    private final Mailer mailer;

    public SendMailNewQuestion(Mailer mailer) {
        this.mailer = mailer;
    }

    public void send(Question question) {
        User questioner = question.getQuestioner();
        mailer.sendText(questioner.getLogin(), "[MercadoLivre] New question about your product", "Hi! We have a new question about your product: \n " + question.getTitle());
    }
}
