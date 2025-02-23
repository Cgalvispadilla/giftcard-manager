package com.exito.giftcardmanager.infrastructure.adapter.database.sql.user.repository;

import com.exito.giftcardmanager.infrastructure.adapter.database.sql.user.data.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    Optional<UserData> findByUserName(String userName);
}
