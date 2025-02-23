package com.exito.giftcardmanager.domain.usecase.user;

import com.exito.giftcardmanager.domain.model.user.TokenResponse;
import com.exito.giftcardmanager.domain.model.user.User;
import com.exito.giftcardmanager.domain.model.user.exception.UserInternalException;
import com.exito.giftcardmanager.domain.model.user.exception.UserInvalidException;
import com.exito.giftcardmanager.domain.model.user.gateway.JwtProvider;
import com.exito.giftcardmanager.domain.model.user.gateway.PasswordEncoder;
import com.exito.giftcardmanager.domain.model.user.gateway.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginUserUseCase  {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public TokenResponse login(String userName, String password) {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UserInvalidException("User not found");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserInvalidException("Invalid password");
        }
        try {
            return jwtProvider.generateToken(userName);
        } catch (Exception e) {
            throw new UserInternalException("Error generating token");
        }

    }
}
