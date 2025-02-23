package com.exito.giftcardmanager.infrastructure.adapter.database.sql.giftcard.adapter;

import com.exito.giftcardmanager.domain.model.giftcard.GiftCard;
import com.exito.giftcardmanager.domain.model.giftcard.gateway.GiftCardRepository;
import com.exito.giftcardmanager.infrastructure.adapter.database.sql.giftcard.mapper.GiftCardDataMapper;
import com.exito.giftcardmanager.infrastructure.adapter.database.sql.giftcard.repository.GiftCardDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class GiftCardRepositoryAdapter implements GiftCardRepository {
    private final GiftCardDataRepository giftCardDataRepository;

    @Override
    public GiftCard save(GiftCard giftCard) {
       var gfSave = giftCardDataRepository.save(GiftCardDataMapper.INSTANCE.toGiftCardData(giftCard));
       return GiftCardDataMapper.INSTANCE.toGiftCardDomain(gfSave);
    }

    @Override
    public Optional<GiftCard> findById(Long id) {
        return giftCardDataRepository.findById(id)
                .map(GiftCardDataMapper.INSTANCE::toGiftCardDomain);
    }

    @Override
    public List<GiftCard> findAll() {
        return giftCardDataRepository.findAll().stream()
                .map(GiftCardDataMapper.INSTANCE::toGiftCardDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void update(GiftCard giftCard) {
        giftCardDataRepository.save(GiftCardDataMapper.INSTANCE.toGiftCardData(giftCard));
    }

    @Override
    public void deleteById(Long id) {
        giftCardDataRepository.deleteById(id);
    }


    @Override
    public void redeem(Long id) {
        giftCardDataRepository.findById(id).ifPresent(giftCardData -> {
            giftCardData.setConsumed(true);
            giftCardDataRepository.save(giftCardData);
        });
    }
}