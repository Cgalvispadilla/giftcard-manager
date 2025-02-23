package com.exito.giftcardmanager.domain.model.user;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder(toBuilder = true)
public class TokenResponse {
    private final String token;
    private final String type ;
    private final Date createdAt;
    private final Date expiration;
}
