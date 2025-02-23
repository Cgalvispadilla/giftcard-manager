package com.exito.giftcardmanager.infrastructure.receiver.web.user.mapper;

import com.exito.giftcardmanager.domain.model.user.User;
import com.exito.giftcardmanager.infrastructure.receiver.web.user.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDTOMapper {
    UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);

    User toDomain(UserDTO userDTO);

    UserDTO toDto(User user);
}
