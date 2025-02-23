package com.exito.giftcardmanager.domain.model.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class User {
    private final String userName;
    private final String password;
}
