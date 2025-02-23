package com.exito.giftcardmanager.infrastructure.receiver.web.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserDTO {
    private String userName;
    private String password;
}
