package com.exito.giftcardmanager.domain.usecase.giftcard;

import com.exito.giftcardmanager.domain.model.giftcard.exception.GiftCardInternalException;
import com.exito.giftcardmanager.domain.model.giftcard.gateway.GiftCardRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteGiftCardUseCase {
    private final GiftCardRepository giftCardRepository;

    public boolean deleteGiftCard(Long id) {
        try {
            if (giftCardRepository.findById(id).isPresent()) {
                giftCardRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new GiftCardInternalException("Error eliminando la GiftCard " + e.getMessage());
        }

    }
}