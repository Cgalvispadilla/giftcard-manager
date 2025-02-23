package com.exito.giftcardmanager.infrastructure.adapter.database.sql.giftcard.repository;

import com.exito.giftcardmanager.infrastructure.adapter.database.sql.giftcard.data.GiftCardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCardDataRepository extends JpaRepository<GiftCardData, Long> {
}