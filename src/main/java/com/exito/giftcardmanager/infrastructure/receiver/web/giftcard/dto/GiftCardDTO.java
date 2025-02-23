package com.exito.giftcardmanager.infrastructure.receiver.web.giftcard.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class GiftCardDTO {
    @NotNull(message = "Code cannot be null")
    @Size(min = 1, message = "Code cannot be empty")
    private String code;
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;
    @NotNull(message = "Email to cannot be null")
    @Email(message = "Email to must be a valid email")
    private String emailTo;
    @NotNull(message = "Expiration date cannot be null")
    private Date expirationDate;

}
