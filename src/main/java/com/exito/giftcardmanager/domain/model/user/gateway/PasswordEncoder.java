package com.exito.giftcardmanager.domain.model.user.gateway;

public interface PasswordEncoder {
    String encode(String password);
    boolean matches(String password, String encodedPassword);
}
