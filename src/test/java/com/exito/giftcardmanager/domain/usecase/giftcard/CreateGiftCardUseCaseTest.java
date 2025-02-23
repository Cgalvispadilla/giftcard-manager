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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateGiftCardUseCaseTest {

    @Mock
    private GiftCardRepository giftCardRepository;

    @Mock
    private SenderEmailGateway senderEmailGateway;

    @InjectMocks
    private CreateGiftCardUseCase createGiftCardUseCase;

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
    void createGiftCardSuccessfully() {
        when(giftCardRepository.save(giftCard)).thenReturn(giftCard);

        GiftCard result = createGiftCardUseCase.createGiftCard(giftCard);

        assertNotNull(result);
        assertEquals("ABC123", result.getCode());
        verify(giftCardRepository, times(1)).save(giftCard);
        verify(senderEmailGateway, times(1)).sendEmail(eq("test@example.com"), anyString(), anyString());
    }

    @Test
    void createGiftCardWithRepositoryError() {
        when(giftCardRepository.save(giftCard)).thenThrow(new RuntimeException("Database error"));

        GiftCardInternalException exception = assertThrows(GiftCardInternalException.class, () -> createGiftCardUseCase.createGiftCard(giftCard));

        assertEquals("Error al crear la tarjeta de regalo Database error", exception.getMessage());
        verify(giftCardRepository, times(1)).save(giftCard);
        verify(senderEmailGateway, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void createGiftCardWithEmailError() {
        when(giftCardRepository.save(giftCard)).thenReturn(giftCard);
        doThrow(new RuntimeException("Email error")).when(senderEmailGateway).sendEmail(anyString(), anyString(), anyString());

        GiftCard result = createGiftCardUseCase.createGiftCard(giftCard);

        assertNotNull(result);
        assertEquals("ABC123", result.getCode());
        verify(giftCardRepository, times(1)).save(giftCard);
        verify(senderEmailGateway, times(1)).sendEmail(eq("test@example.com"), anyString(), anyString());
    }
}