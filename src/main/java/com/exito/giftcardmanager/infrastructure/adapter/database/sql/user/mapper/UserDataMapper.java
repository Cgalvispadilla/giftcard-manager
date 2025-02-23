package com.exito.giftcardmanager.infrastructure.adapter.database.sql.user.mapper;

import com.exito.giftcardmanager.domain.model.user.User;
import com.exito.giftcardmanager.infrastructure.adapter.database.sql.user.data.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDataMapper {

    UserDataMapper INSTANCE = Mappers.getMapper(UserDataMapper.class);

    UserData toUserData(User user);

    User toUserDomain(UserData userData);
}
