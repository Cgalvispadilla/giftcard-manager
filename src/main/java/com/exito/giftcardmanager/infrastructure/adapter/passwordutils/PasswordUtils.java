package com.exito.giftcardmanager.infrastructure.adapter.passwordutils;

import com.exito.giftcardmanager.domain.model.user.gateway.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordUtils implements PasswordEncoder {
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}
