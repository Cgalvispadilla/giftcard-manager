package com.exito.giftcardmanager.domain.usecase.giftcard;

import com.exito.giftcardmanager.domain.model.giftcard.GiftCard;
import com.exito.giftcardmanager.domain.model.giftcard.exception.GiftCardInternalException;
import com.exito.giftcardmanager.domain.model.giftcard.gateway.GiftCardRepository;
import com.exito.giftcardmanager.domain.model.giftcard.gateway.SenderEmailGateway;
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

class RedeemGiftCardUseCaseTest {

    @Mock
    private GiftCardRepository giftCardRepository;

    @Mock
    private SenderEmailGateway senderEmailGateway;

    @InjectMocks
    private RedeemGiftCardUseCase redeemGiftCardUseCase;

    private GiftCard giftCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        giftCard = GiftCard.builder()
                .id(1L)
                .code("ABC123")
                .emailTo("test@example.com")
                .amount(100.0)
                .expirationDate(Date.valueOf(LocalDate.now()))
                .build();
    }

    @Test
    void redeemGiftCardSuccessfully() {
        Long id = 1L;
        when(giftCardRepository.findById(id)).thenReturn(Optional.of(giftCard));

        boolean result = redeemGiftCardUseCase.redeemGiftCard(id);

        assertTrue(result);
        verify(giftCardRepository, times(1)).redeem(id);
        verify(senderEmailGateway, times(1)).sendEmail(eq("test@example.com"), eq("Gift Card Redimida"), anyString());
    }

    @Test
    void redeemGiftCardNotFound() {
        Long id = 1L;
        when(giftCardRepository.findById(id)).thenReturn(Optional.empty());

        boolean result = redeemGiftCardUseCase.redeemGiftCard(id);

        assertFalse(result);
        verify(giftCardRepository, never()).redeem(id);
        verify(senderEmailGateway, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void redeemGiftCardWithRepositoryError() {
        Long id = 1L;
        when(giftCardRepository.findById(id)).thenReturn(Optional.of(giftCard));
        doThrow(new RuntimeException("Database error")).when(giftCardRepository).redeem(id);

        GiftCardInternalException exception = assertThrows(GiftCardInternalException.class, () -> redeemGiftCardUseCase.redeemGiftCard(id));

        assertEquals("Error al redimir la GiftCard Database error", exception.getMessage());
        verify(giftCardRepository, times(1)).redeem(id);
        verify(senderEmailGateway, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void redeemGiftCardWithEmailError() {
        Long id = 1L;
        when(giftCardRepository.findById(id)).thenReturn(Optional.of(giftCard));
        doNothing().when(giftCardRepository).redeem(id);
        doThrow(new RuntimeException("Email error")).when(senderEmailGateway).sendEmail(anyString(), anyString(), anyString());

        boolean result = redeemGiftCardUseCase.redeemGiftCard(id);

        assertTrue(result);
        verify(giftCardRepository, times(1)).redeem(id);
        verify(senderEmailGateway, times(1)).sendEmail(eq("test@example.com"), eq("Gift Card Redimida"), anyString());
    }
}