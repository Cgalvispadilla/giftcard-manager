package com.exito.giftcardmanager.infrastructure.adapter.database.sql.user.adapter;

import com.exito.giftcardmanager.domain.model.user.User;
import com.exito.giftcardmanager.domain.model.user.gateway.UserRepository;
import com.exito.giftcardmanager.infrastructure.adapter.database.sql.user.mapper.UserDataMapper;
import com.exito.giftcardmanager.infrastructure.adapter.database.sql.user.repository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDataRepositoryAdapter implements UserRepository {
    private final UserDataRepository userDataRepository;

    @Override
    public void save(User user) {
        userDataRepository.save(UserDataMapper.INSTANCE.toUserData(user));
    }

    @Override
    public User findByUserName(String userName) {
        return userDataRepository.findByUserName(userName)
                .map(UserDataMapper.INSTANCE::toUserDomain)
                .orElse(null);
    }
}
