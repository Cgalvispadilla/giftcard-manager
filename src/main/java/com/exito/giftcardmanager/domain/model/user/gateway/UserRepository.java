package com.exito.giftcardmanager.domain.model.user.gateway;

import com.exito.giftcardmanager.domain.model.user.User;

public interface UserRepository {
    void save(User user);
    User findByUserName(String userName);

}
