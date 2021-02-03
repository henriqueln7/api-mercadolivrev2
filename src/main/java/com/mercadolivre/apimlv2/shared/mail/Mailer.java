package com.mercadolivre.apimlv2.shared.mail;

public interface Mailer {
    void sendText(String to, String subject, String body);
}
