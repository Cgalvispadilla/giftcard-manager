package com.exito.giftcardmanager.infrastructure.receiver.web.giftcard.controller;

import com.exito.giftcardmanager.domain.model.giftcard.GiftCard;
import com.exito.giftcardmanager.domain.usecase.giftcard.*;
import com.exito.giftcardmanager.infrastructure.receiver.web.common.BuilderResponseDTO;
import com.exito.giftcardmanager.infrastructure.receiver.web.giftcard.dto.GiftCardDTO;
import com.exito.giftcardmanager.infrastructure.receiver.web.giftcard.dto.GiftCardUpdateDTO;
import com.exito.giftcardmanager.infrastructure.receiver.web.giftcard.mapper.GiftCardMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/giftcards")
@RequiredArgsConstructor
public class GiftCardController implements BuilderResponseDTO {
    private final CreateGiftCardUseCase createGiftCardUseCase;
    private final GetGiftCardUseCase getGiftCardUseCase;
    private final UpdateGiftCardUseCase updateGiftCardUseCase;
    private final DeleteGiftCardUseCase deleteGiftCardUseCase;
    private final RedeemGiftCardUseCase redeemGiftCardUseCase;

    @PostMapping
    public ResponseEntity<?> createGiftCard(@RequestBody @Valid GiftCardDTO giftCard) {
        var res = createGiftCardUseCase.createGiftCard(GiftCardMapper.INSTANCE.toGiftCardDomain(giftCard));
        return ResponseEntity.ok(buildResponseDTO(res, "Gift card created successfully", String.valueOf(HttpStatus.CREATED.value())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGiftCardById(@PathVariable Long id) {
        Optional<GiftCard> giftCard = getGiftCardUseCase.getGiftCardById(id);
        return giftCard.map(giftCard1 -> ResponseEntity.ok(buildResponseDTO(giftCard1, "Gift card found", String.valueOf(HttpStatus.OK.value()))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> getAllGiftCards() {
        List<GiftCard> giftCards = getGiftCardUseCase.getAllGiftCards();
        return ResponseEntity.ok(buildResponseDTO(giftCards, "Gift cards found", String.valueOf(HttpStatus.OK.value())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGiftCard(@PathVariable Long id, @RequestBody @Valid GiftCardUpdateDTO giftCardDTO) {
        GiftCard updatedGiftCard = GiftCardMapper.INSTANCE.toGiftCardDomain(giftCardDTO);
        boolean isUpdated = updateGiftCardUseCase.updateGiftCard(id, updatedGiftCard);
        if (isUpdated) {
            return ResponseEntity.ok(buildResponseDTO("Gift card updated successfully", "Success", String.valueOf(HttpStatus.OK.value())));
        } else {
            return new ResponseEntity<>(buildResponseDTO("Gift card not found", "Error", String.valueOf(HttpStatus.NOT_FOUND)), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGiftCard(@PathVariable Long id) {
        boolean isDeleted = deleteGiftCardUseCase.deleteGiftCard(id);
        if (isDeleted) {
            return ResponseEntity.ok(buildResponseDTO("Gift card deleted successfully", "Success", String.valueOf(HttpStatus.OK.value())));
        } else {
            return new ResponseEntity<>(buildResponseDTO("Gift card not found", "Error", String.valueOf(HttpStatus.NOT_FOUND)), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/{id}/redeem")
    public ResponseEntity<?> redeemGiftCard(@PathVariable Long id) {
        boolean isRedeemed = redeemGiftCardUseCase.redeemGiftCard(id);
        if (isRedeemed) {
            return ResponseEntity.ok(buildResponseDTO("Gift card redeemed successfully", "Success", String.valueOf(HttpStatus.OK.value())));
        } else {
            return new ResponseEntity<>(buildResponseDTO("Gift card not found", "Error", String.valueOf(HttpStatus.NOT_FOUND)), HttpStatus.NOT_FOUND);
        }
    }
}
