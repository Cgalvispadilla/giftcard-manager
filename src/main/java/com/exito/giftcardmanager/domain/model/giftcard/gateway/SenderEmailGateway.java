package com.exito.giftcardmanager.domain.model.giftcard.gateway;

public interface SenderEmailGateway {
    void sendEmail(String email, String subject, String message);
}
