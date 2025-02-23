package com.exito.giftcardmanager.domain.usecase.giftcard;

import com.exito.giftcardmanager.domain.model.giftcard.exception.GiftCardInternalException;
import com.exito.giftcardmanager.domain.model.giftcard.gateway.GiftCardRepository;
import com.exito.giftcardmanager.domain.model.giftcard.gateway.SenderEmailGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class RedeemGiftCardUseCase {
    private final GiftCardRepository giftCardRepository;
    private final SenderEmailGateway senderEmailGateway;

    public boolean redeemGiftCard(Long id) {
        var giftCard = giftCardRepository.findById(id);
        if (giftCard.isPresent()) {
            try {
                giftCardRepository.redeem(id);
            } catch (Exception e) {
                throw new GiftCardInternalException("Error al redimir la GiftCard " + e.getMessage());
            }

            try {
                senderEmailGateway.sendEmail(giftCard.get().getEmailTo(), "Gift Card Redimida",
                        "Ha sido redimida la gift card con el código: " + giftCard.get().getCode());
            } catch (Exception e) {
                log.error("Error al enviar el correo", e);
            }
            return true;
        } else {
            return false;
        }
    }
}
