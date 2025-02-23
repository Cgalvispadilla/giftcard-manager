package com.exito.giftcardmanager.infrastructure.receiver.web.giftcard.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class GiftCardUpdateDTO {
    @Positive(message = "Amount must be positive")
    private Double amount;
    private Boolean consumed;
}
