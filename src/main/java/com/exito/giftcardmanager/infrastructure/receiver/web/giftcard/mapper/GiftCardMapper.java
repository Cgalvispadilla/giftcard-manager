package com.exito.giftcardmanager.infrastructure.receiver.web.giftcard.mapper;

import com.exito.giftcardmanager.domain.model.giftcard.GiftCard;
import com.exito.giftcardmanager.infrastructure.receiver.web.giftcard.dto.GiftCardDTO;
import com.exito.giftcardmanager.infrastructure.receiver.web.giftcard.dto.GiftCardUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GiftCardMapper {
    GiftCardMapper INSTANCE = Mappers.getMapper(GiftCardMapper.class);

    GiftCard toGiftCardDomain(GiftCardDTO giftCardDTO);
    GiftCard toGiftCardDomain(GiftCardUpdateDTO giftCardUpdateDTO);
}
