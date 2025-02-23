package com.exito.giftcardmanager.domain.usecase.giftcard;

import com.exito.giftcardmanager.domain.model.giftcard.GiftCard;
import com.exito.giftcardmanager.domain.model.giftcard.exception.GiftCardInternalException;
import com.exito.giftcardmanager.domain.model.giftcard.gateway.GiftCardRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class GetGiftCardUseCase {
    private final GiftCardRepository giftCardRepository;

    public Optional<GiftCard> getGiftCardById(Long id) {

        try {
            return giftCardRepository.findById(id);
        } catch (Exception e) {
            throw new GiftCardInternalException("Error obteniendo la GiftCard por id " + e.getMessage());
        }

    }

    public List<GiftCard> getAllGiftCards() {
        try {
        return giftCardRepository.findAll();
        } catch (Exception e) {
            throw new GiftCardInternalException("Error obteniendo todas las GiftCard " + e.getMessage());
        }
    }
}