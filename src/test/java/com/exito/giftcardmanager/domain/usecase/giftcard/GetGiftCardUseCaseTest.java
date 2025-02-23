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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetGiftCardUseCaseTest {

    @Mock
    private GiftCardRepository giftCardRepository;

    @InjectMocks
    private GetGiftCardUseCase getGiftCardUseCase;

    private GiftCard giftCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        giftCard = GiftCard.builder()
                .code("ABC123")
                .emailTo("test@example.com")
                .amount(100.0)
                .expirationDate(Date.valueOf(LocalDate.now()))
                .build();
    }

    @Test
    void getGiftCardByIdSuccessfully() {
        Long id = 1L;
        when(giftCardRepository.findById(id)).thenReturn(Optional.of(giftCard));

        Optional<GiftCard> result = getGiftCardUseCase.getGiftCardById(id);

        assertTrue(result.isPresent());
        assertEquals("ABC123", result.get().getCode());
        verify(giftCardRepository, times(1)).findById(id);
    }

    @Test
    void getGiftCardByIdNotFound() {
        Long id = 1L;
        when(giftCardRepository.findById(id)).thenReturn(Optional.empty());

        Optional<GiftCard> result = getGiftCardUseCase.getGiftCardById(id);

        assertFalse(result.isPresent());
        verify(giftCardRepository, times(1)).findById(id);
    }

    @Test
    void getGiftCardByIdWithRepositoryError() {
        Long id = 1L;
        when(giftCardRepository.findById(id)).thenThrow(new RuntimeException("Database error"));

        GiftCardInternalException exception = assertThrows(GiftCardInternalException.class, () -> getGiftCardUseCase.getGiftCardById(id));

        assertEquals("Error obteniendo la GiftCard por id Database error", exception.getMessage());
        verify(giftCardRepository, times(1)).findById(id);
    }

    @Test
    void getAllGiftCardsSuccessfully() {
        when(giftCardRepository.findAll()).thenReturn(Collections.singletonList(giftCard));

        List<GiftCard> result = getGiftCardUseCase.getAllGiftCards();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ABC123", result.get(0).getCode());
        verify(giftCardRepository, times(1)).findAll();
    }

    @Test
    void getAllGiftCardsEmpty() {
        when(giftCardRepository.findAll()).thenReturn(Collections.emptyList());

        List<GiftCard> result = getGiftCardUseCase.getAllGiftCards();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(giftCardRepository, times(1)).findAll();
    }

    @Test
    void getAllGiftCardsWithRepositoryError() {
        when(giftCardRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        GiftCardInternalException exception = assertThrows(GiftCardInternalException.class, () -> getGiftCardUseCase.getAllGiftCards());

        assertEquals("Error obteniendo todas las GiftCard Database error", exception.getMessage());
        verify(giftCardRepository, times(1)).findAll();
    }
}