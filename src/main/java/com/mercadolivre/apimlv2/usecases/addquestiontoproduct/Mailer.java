package com.mercadolivre.apimlv2.usecases.addquestiontoproduct;

public interface Mailer {
    void sendText(String to, String subject, String body);
}
