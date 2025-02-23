package com.exito.giftcardmanager.infrastructure.receiver.web.common;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class ResponseDTO<T> {
    private T data;
    private String message;
    private String status;
}
