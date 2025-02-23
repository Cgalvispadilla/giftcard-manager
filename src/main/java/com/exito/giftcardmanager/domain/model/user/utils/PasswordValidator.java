package com.exito.giftcardmanager.domain.model.user.utils;

import java.util.regex.Pattern;

public interface PasswordValidator {
    final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    default boolean isStrongPassword(String password) {
        if (password == null) {
            return false;
        }
        return pattern.matcher(password).matches();
    }
}
