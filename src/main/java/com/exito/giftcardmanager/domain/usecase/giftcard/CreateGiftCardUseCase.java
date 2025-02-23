package com.exito.giftcardmanager.domain.usecase.giftcard;

import com.exito.giftcardmanager.domain.model.giftcard.GiftCard;
import com.exito.giftcardmanager.domain.model.giftcard.exception.GiftCardInternalException;
import com.exito.giftcardmanager.domain.model.giftcard.gateway.GiftCardRepository;
import com.exito.giftcardmanager.domain.model.giftcard.gateway.SenderEmailGateway;
import com.exito.giftcardmanager.domain.model.giftcard.utils.EmailFormater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CreateGiftCardUseCase implements EmailFormater {
    private final GiftCardRepository giftCardRepository;
    private final SenderEmailGateway senderEmailGateway;

    public GiftCard createGiftCard(GiftCard giftCard) {
        GiftCard res;
        try {
            res = giftCardRepository.save(giftCard);
        } catch (Exception e) {
            throw new GiftCardInternalException("Error al crear la tarjeta de regalo " + e.getMessage());
        }

        try {
            var result = getResult(res);

            senderEmailGateway.sendEmail(res.getEmailTo(), "Le han regalado una tarjeta de regalo",
                    "El código de la tarjeta de regalo es: " + res.getCode() + " la cual tiene un cupo de: " + result.formattedAmount()
                            + " y una fecha de expiración de: " + result.formattedDate());
        } catch (Exception e) {
            log.error("Error al enviar el correo", e);
        }

        return res;
    }
}