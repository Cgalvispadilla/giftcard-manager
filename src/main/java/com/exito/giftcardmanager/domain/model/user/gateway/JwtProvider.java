package com.exito.giftcardmanager.domain.model.user.gateway;

import com.exito.giftcardmanager.domain.model.user.TokenResponse;

public interface JwtProvider {
    TokenResponse generateToken(String userName);
}
