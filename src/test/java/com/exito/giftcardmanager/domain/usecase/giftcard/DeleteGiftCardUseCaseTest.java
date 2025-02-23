package com.exito.giftcardmanager.domain.usecase.giftcard;

import com.exito.giftcardmanager.domain.model.giftcard.GiftCard;
import com.exito.giftcardmanager.domain.model.giftcard.exception.GiftCardInternalException;
import com.exito.giftcardmanager.domain.model.giftcard.gateway.GiftCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteGiftCardUseCaseTest {

    @Mock
    private GiftCardRepository giftCardRepository;

    @InjectMocks
    private DeleteGiftCardUseCase deleteGiftCardUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteGiftCardSuccessfully() {
        Long id = 1L;
        when(giftCardRepository.findById(id)).thenReturn(Optional.of(GiftCard.builder().build()));

        boolean result = deleteGiftCardUseCase.deleteGiftCard(id);

        assertTrue(result);
        verify(giftCardRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteGiftCardNotFound() {
        Long id = 1L;
        when(giftCardRepository.findById(id)).thenReturn(Optional.empty());

        boolean result = deleteGiftCardUseCase.deleteGiftCard(id);

        assertFalse(result);
        verify(giftCardRepository, never()).deleteById(id);
    }

    @Test
    void deleteGiftCardWithRepositoryError() {
        Long id = 1L;
        when(giftCardRepository.findById(id)).thenReturn(Optional.of(GiftCard.builder().build()));
        doThrow(new RuntimeException("Database error")).when(giftCardRepository).deleteById(id);

        GiftCardInternalException exception = assertThrows(GiftCardInternalException.class, () -> deleteGiftCardUseCase.deleteGiftCard(id));

        assertEquals("Error eliminando la GiftCard Database error", exception.getMessage());
        verify(giftCardRepository, times(1)).deleteById(id);
    }
}