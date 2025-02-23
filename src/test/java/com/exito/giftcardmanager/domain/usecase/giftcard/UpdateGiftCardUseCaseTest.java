package com.exito.giftcardmanager.domain.usecase.giftcard;

import com.exito.giftcardmanager.domain.model.giftcard.GiftCard;
import com.exito.giftcardmanager.domain.model.giftcard.exception.GiftCardInternalException;
import com.exito.giftcardmanager.domain.model.giftcard.gateway.GiftCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateGiftCardUseCaseTest {

    @Mock
    private GiftCardRepository giftCardRepository;

    @InjectMocks
    private UpdateGiftCardUseCase updateGiftCardUseCase;

    private GiftCard existingGiftCard;
    private GiftCard updatedGiftCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        existingGiftCard = GiftCard.builder()
                .id(1L)
                .amount(100.0)
                .consumed(false)
                .build();
        updatedGiftCard = GiftCard.builder()
                .amount(200.0)
                .consumed(false)
                .build();
    }

    @Test
    void updateGiftCardSuccessfully() {
        Long id = 1L;
        when(giftCardRepository.findById(id)).thenReturn(Optional.of(existingGiftCard));

        boolean result = updateGiftCardUseCase.updateGiftCard(id, updatedGiftCard);

        assertTrue(result);
        verify(giftCardRepository, times(1)).update(any(GiftCard.class));
    }

    @Test
    void updateGiftCardNotFound() {
        Long id = 1L;
        when(giftCardRepository.findById(id)).thenReturn(Optional.empty());

        boolean result = updateGiftCardUseCase.updateGiftCard(id, updatedGiftCard);

        assertFalse(result);
        verify(giftCardRepository, never()).update(any(GiftCard.class));
    }

    @Test
    void updateGiftCardWithRepositoryError() {
        Long id = 1L;
        when(giftCardRepository.findById(id)).thenReturn(Optional.of(existingGiftCard));
        doThrow(new RuntimeException("Database error")).when(giftCardRepository).update(any(GiftCard.class));

        GiftCardInternalException exception = assertThrows(GiftCardInternalException.class, () -> updateGiftCardUseCase.updateGiftCard(id, updatedGiftCard));

        assertEquals("Error al actualizar la GiftCard Database error", exception.getMessage());
        verify(giftCardRepository, times(1)).update(any(GiftCard.class));
    }
}