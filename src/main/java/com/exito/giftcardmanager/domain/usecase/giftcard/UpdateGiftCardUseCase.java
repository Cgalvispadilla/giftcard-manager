package com.exito.giftcardmanager.domain.usecase.giftcard;

import com.exito.giftcardmanager.domain.model.giftcard.GiftCard;
import com.exito.giftcardmanager.domain.model.giftcard.exception.GiftCardInternalException;
import com.exito.giftcardmanager.domain.model.giftcard.gateway.GiftCardRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UpdateGiftCardUseCase {
    private final GiftCardRepository giftCardRepository;

    public boolean updateGiftCard(Long id, GiftCard updatedGiftCard) {
        try {
            Optional<GiftCard> existingGiftCard = giftCardRepository.findById(id);
            if (existingGiftCard.isPresent()) {
                GiftCard giftCardToUpdate = existingGiftCard.get().toBuilder()
                        .amount(updatedGiftCard.getAmount() != null ? updatedGiftCard.getAmount() : existingGiftCard.get().getAmount())
                        .consumed(updatedGiftCard.getConsumed() != null ? updatedGiftCard.getConsumed() : existingGiftCard.get().getConsumed())
                        .build();
                giftCardRepository.update(giftCardToUpdate);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new GiftCardInternalException("Error al actualizar la GiftCard " + e.getMessage());
        }
    }
}