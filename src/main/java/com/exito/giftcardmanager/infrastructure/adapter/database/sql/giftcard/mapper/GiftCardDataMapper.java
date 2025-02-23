package com.exito.giftcardmanager.infrastructure.adapter.database.sql.giftcard.mapper;

import com.exito.giftcardmanager.domain.model.giftcard.GiftCard;
import com.exito.giftcardmanager.infrastructure.adapter.database.sql.giftcard.data.GiftCardData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GiftCardDataMapper {
    GiftCardDataMapper INSTANCE = Mappers.getMapper(GiftCardDataMapper.class);

    GiftCardData toGiftCardData(GiftCard giftCard);
    @Mapping(target = "id", source = "id")
    GiftCard toGiftCardDomain(GiftCardData giftCardData);
}