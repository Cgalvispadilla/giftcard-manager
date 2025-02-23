package com.exito.giftcardmanager.infrastructure.receiver.web.user.controller;

import com.exito.giftcardmanager.domain.usecase.user.LoginUserUseCase;
import com.exito.giftcardmanager.domain.usecase.user.RegisterUserUseCase;
import com.exito.giftcardmanager.infrastructure.receiver.web.common.BuilderResponseDTO;
import com.exito.giftcardmanager.infrastructure.receiver.web.common.ResponseDTO;
import com.exito.giftcardmanager.infrastructure.receiver.web.user.dto.UserDTO;
import com.exito.giftcardmanager.infrastructure.receiver.web.user.mapper.UserDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController implements BuilderResponseDTO {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private static final String MESSAGE = "Token generado correctamente";

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDto) {
        var response = registerUserUseCase.registerUserAndGenerateToken(UserDTOMapper.INSTANCE.toDomain(userDto));
        return ResponseEntity.ok(buildResponseDTO(response, MESSAGE, String.valueOf(HttpStatus.OK.value())));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDto) {
        var response = loginUserUseCase.login(userDto.getUserName(), userDto.getPassword());
        return ResponseEntity.ok(buildResponseDTO(response, MESSAGE, String.valueOf(HttpStatus.OK.value())));
    }

}