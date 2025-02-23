package com.exito.giftcardmanager.application.UseCaseConfig;

import com.exito.giftcardmanager.domain.model.giftcard.gateway.GiftCardRepository;
import com.exito.giftcardmanager.domain.model.giftcard.gateway.SenderEmailGateway;
import com.exito.giftcardmanager.domain.usecase.giftcard.*;
import com.exito.giftcardmanager.domain.usecase.user.LoginUserUseCase;
import com.exito.giftcardmanager.domain.usecase.user.RegisterUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    protected RegisterUserUseCase registerUserUseCase(com.exito.giftcardmanager.domain.model.user.gateway.UserRepository userRepository,
                                                      com.exito.giftcardmanager.domain.model.user.gateway.JwtProvider jwtProvider,
                                                      com.exito.giftcardmanager.domain.model.user.gateway.PasswordEncoder passwordEncoder) {
        return new RegisterUserUseCase(userRepository, jwtProvider, passwordEncoder);
    }

    @Bean
    protected LoginUserUseCase loginUserUseCase(com.exito.giftcardmanager.domain.model.user.gateway.UserRepository userRepository,
                                                com.exito.giftcardmanager.domain.model.user.gateway.JwtProvider jwtProvider,
                                                com.exito.giftcardmanager.domain.model.user.gateway.PasswordEncoder passwordEncoder) {
        return new LoginUserUseCase(userRepository, jwtProvider, passwordEncoder);
    }

    @Bean
    protected CreateGiftCardUseCase createGiftCardUseCase(GiftCardRepository giftCardRepository,
                                                          SenderEmailGateway senderEmailGateway) {
        return new CreateGiftCardUseCase(giftCardRepository, senderEmailGateway);
    }

    @Bean
    protected GetGiftCardUseCase getGiftCardUseCase(GiftCardRepository giftCardRepository) {
        return new GetGiftCardUseCase(giftCardRepository);
    }

    @Bean
    protected UpdateGiftCardUseCase updateGiftCardUseCase(GiftCardRepository giftCardRepository) {
        return new UpdateGiftCardUseCase(giftCardRepository);
    }

    @Bean
    protected DeleteGiftCardUseCase deleteGiftCardUseCase(GiftCardRepository giftCardRepository) {
        return new DeleteGiftCardUseCase(giftCardRepository);
    }

    @Bean
    protected RedeemGiftCardUseCase redeemGiftCardUseCase(GiftCardRepository giftCardRepository,
                                                          SenderEmailGateway senderEmailGateway) {
        return new RedeemGiftCardUseCase(giftCardRepository, senderEmailGateway);
    }
}
