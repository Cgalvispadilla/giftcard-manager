package com.exito.giftcardmanager.infrastructure.receiver.web.user.exception;

import com.exito.giftcardmanager.domain.model.user.exception.UserInternalException;
import com.exito.giftcardmanager.domain.model.user.exception.UserInvalidException;
import com.exito.giftcardmanager.infrastructure.receiver.web.common.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerExceptionHandler {
    @ExceptionHandler(UserInvalidException.class)
    public ResponseEntity<?> handleUserInvalidException(RuntimeException ex) {
        return new ResponseEntity<>(buildResponseDTO(ex.getMessage(), HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserInternalException.class)
    public ResponseEntity<?> handleUserInternalError(Exception ex) {
        return new ResponseEntity<>(buildResponseDTO(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return new ResponseEntity<>(buildResponseDTO(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseDTO<?> buildResponseDTO(String message, HttpStatus status) {
        return ResponseDTO.builder()
                .data(null)
                .message(message)
                .status(String.valueOf(status.value()))
                .build();
    }
}
