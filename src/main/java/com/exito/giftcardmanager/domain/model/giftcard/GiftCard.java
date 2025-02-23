package com.exito.giftcardmanager.domain.model.giftcard;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder(toBuilder = true)
public class GiftCard {
    private final Long id;
    private final String code;
    private final Double amount;
    private final String emailTo;
    private final Date creationDate;
    private final Date expirationDate;
    private final Boolean consumed;
}