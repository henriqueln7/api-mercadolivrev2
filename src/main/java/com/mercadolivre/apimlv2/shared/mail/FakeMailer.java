package com.mercadolivre.apimlv2.shared.mail;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
@Primary
public class FakeMailer implements Mailer {
    @Override
    public void sendText(String to, String subject, String body) {
        System.out.println(MessageFormat.format("Sending email to {0}. \n Subject: {1}. \n Email body: {2}", to, subject, body));
    }
}
