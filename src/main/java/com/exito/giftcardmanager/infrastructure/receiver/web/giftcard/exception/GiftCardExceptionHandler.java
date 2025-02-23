package com.exito.giftcardmanager.infrastructure.receiver.web.giftcard.exception;

import com.exito.giftcardmanager.domain.model.giftcard.exception.GiftCardInternalException;
import com.exito.giftcardmanager.infrastructure.receiver.web.common.BuilderResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GiftCardExceptionHandler implements BuilderResponseDTO {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());


        return new ResponseEntity<>(buildResponseDTO(details, "Validation Failed",
                String.valueOf(HttpStatus.BAD_REQUEST)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GiftCardInternalException.class)
    public ResponseEntity<?> handleValidationGiftCardInternalException(GiftCardInternalException ex) {
        return new ResponseEntity<>(buildResponseDTO(ex.getMessage(), "ERROR", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR)),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
