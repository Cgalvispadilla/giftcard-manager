package com.exito.giftcardmanager.infrastructure.adapter.emailsender;

import com.exito.giftcardmanager.domain.model.giftcard.gateway.SenderEmailGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SenderEmail implements SenderEmailGateway {

    private final JavaMailSender emailSender;

    @Override
    public void sendEmail(String to, String subject, String message) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);

            emailSender.send(simpleMailMessage);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }
}
