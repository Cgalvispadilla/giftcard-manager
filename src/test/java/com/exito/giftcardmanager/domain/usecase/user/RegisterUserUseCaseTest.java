package com.exito.giftcardmanager.domain.usecase.user;

import com.exito.giftcardmanager.domain.model.user.TokenResponse;
import com.exito.giftcardmanager.domain.model.user.User;
import com.exito.giftcardmanager.domain.model.user.exception.UserInternalException;
import com.exito.giftcardmanager.domain.model.user.exception.UserInvalidException;
import com.exito.giftcardmanager.domain.model.user.gateway.JwtProvider;
import com.exito.giftcardmanager.domain.model.user.gateway.PasswordEncoder;
import com.exito.giftcardmanager.domain.model.user.gateway.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUserWithValidData() {
        User user = User.builder()
                .userName("validUser")
                .password("ValidPassword1!&")
                .build();

        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(jwtProvider.generateToken(user.getUserName())).thenReturn(TokenResponse.builder()
                .token("token")
                .build());

        TokenResponse tokenResponse = registerUserUseCase.registerUserAndGenerateToken(user);

        assertNotNull(tokenResponse);
        assertEquals("token", tokenResponse.getToken());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUserWithEmptyUsername() {
        User user = User.builder()
                .userName("")
                .password("ValidPassword1!")
                .build();

        assertThrows(UserInvalidException.class, () -> registerUserUseCase.registerUserAndGenerateToken(user));
    }

    @Test
    void registerUserWithEmptyPassword() {
        User user = User.builder()
                .userName("validUser")
                .password("")
                .build();

        assertThrows(UserInvalidException.class, () -> registerUserUseCase.registerUserAndGenerateToken(user));
    }

    @Test
    void registerUserWithWeakPassword() {
        User user = User.builder()
                .userName("validUser")
                .password("weak")
                .build();

        assertThrows(UserInvalidException.class, () -> registerUserUseCase.registerUserAndGenerateToken(user));
    }

    @Test
    void registerUserWithRepositoryError() {
        User user = User.builder()
                .userName("validUser")
                .password("ValidPassword1!&")
                .build();

        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        doThrow(new RuntimeException()).when(userRepository).save(any(User.class));

        assertThrows(UserInternalException.class, () -> registerUserUseCase.registerUserAndGenerateToken(user));
    }

    @Test
    void registerUserWithTokenGenerationError() {
        User user = User.builder()
                .userName("validUser")
                .password("ValidPassword1!&")
                .build();

        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        doNothing().when(userRepository).save(any(User.class));
        when(jwtProvider.generateToken(user.getUserName())).thenThrow(new RuntimeException());

        assertThrows(UserInternalException.class, () -> registerUserUseCase.registerUserAndGenerateToken(user));
    }
}