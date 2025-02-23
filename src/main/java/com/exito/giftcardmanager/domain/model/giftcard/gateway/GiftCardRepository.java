package com.exito.giftcardmanager.domain.model.giftcard.gateway;

import com.exito.giftcardmanager.domain.model.giftcard.GiftCard;

import java.util.List;
import java.util.Optional;

public interface GiftCardRepository {
    GiftCard save(GiftCard giftCard);

    Optional<GiftCard> findById(Long id);

    List<GiftCard> findAll();

    void update(GiftCard giftCard);

    void deleteById(Long id);

    void redeem(Long id);
}