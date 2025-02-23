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

class LoginUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginUserUseCase loginUserUseCase;

    User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder()
                .password("encodedPassword")
                .userName("validUser")
                .build();
    }

    @Test
    void loginWithValidCredentials() {
        String userName = "validUser";
        String password = "validPassword";

        when(userRepository.findByUserName(userName)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(jwtProvider.generateToken(userName)).thenReturn(TokenResponse.builder()
                .token("token")
                .build());

        TokenResponse tokenResponse = loginUserUseCase.login(userName, password);

        assertNotNull(tokenResponse);
        assertEquals("token", tokenResponse.getToken());
    }

    @Test
    void loginWithInvalidUser() {
        String userName = "invalidUser";
        String password = "password";

        when(userRepository.findByUserName(userName)).thenReturn(null);

        assertThrows(UserInvalidException.class, () -> loginUserUseCase.login(userName, password));
    }

    @Test
    void loginWithInvalidPassword() {
        String userName = "validUser";
        String password = "invalidPassword";


        when(userRepository.findByUserName(userName)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        assertThrows(UserInvalidException.class, () -> loginUserUseCase.login(userName, password));
    }

    @Test
    void loginWithTokenGenerationError() {
        String userName = "validUser";
        String password = "validPassword";


        when(userRepository.findByUserName(userName)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(jwtProvider.generateToken(userName)).thenThrow(new RuntimeException());

        assertThrows(UserInternalException.class, () -> loginUserUseCase.login(userName, password));
    }
}