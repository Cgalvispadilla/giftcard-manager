package com.exito.giftcardmanager.domain.model.user.exception;

public class UserInvalidException extends RuntimeException {
    public UserInvalidException(String message) {
        super(message);
    }
}
