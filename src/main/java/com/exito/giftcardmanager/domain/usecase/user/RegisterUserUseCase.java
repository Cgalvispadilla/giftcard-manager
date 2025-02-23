package com.exito.giftcardmanager.domain.usecase.user;

import com.exito.giftcardmanager.domain.model.user.TokenResponse;
import com.exito.giftcardmanager.domain.model.user.User;
import com.exito.giftcardmanager.domain.model.user.exception.UserInternalException;
import com.exito.giftcardmanager.domain.model.user.exception.UserInvalidException;
import com.exito.giftcardmanager.domain.model.user.gateway.JwtProvider;
import com.exito.giftcardmanager.domain.model.user.gateway.UserRepository;
import com.exito.giftcardmanager.domain.model.user.utils.PasswordValidator;
import lombok.RequiredArgsConstructor;
import com.exito.giftcardmanager.domain.model.user.gateway.PasswordEncoder;

import static com.exito.giftcardmanager.domain.model.common.StringUtils.isNullOrEmpty;

@RequiredArgsConstructor
public class RegisterUserUseCase implements PasswordValidator {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public TokenResponse registerUserAndGenerateToken(User user) {
        if (isNullOrEmpty(user.getUserName()) || isNullOrEmpty(user.getPassword())) {
            throw new UserInvalidException("Username and password must not be empty");
        }
        if (!isStrongPassword(user.getPassword())) {
            throw new UserInvalidException("Password must have at least 8 characters, 1 uppercase letter, " +
                    "1 lowercase letter, 1 number and 1 special character");
        }
        try {
            userRepository.save(user.toBuilder().password(passwordEncoder.encode(user.getPassword())).build());
        } catch (Exception e) {
            throw new UserInternalException("Error creating user " + e.getMessage());
        }
        try {
            return jwtProvider.generateToken(user.getUserName());
        } catch (Exception e) {
            throw new UserInternalException("Error generating token " + e.getMessage());
        }

    }
}
