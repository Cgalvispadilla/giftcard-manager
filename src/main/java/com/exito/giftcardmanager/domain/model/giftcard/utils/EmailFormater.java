package com.exito.giftcardmanager.domain.model.giftcard.utils;

import com.exito.giftcardmanager.domain.model.giftcard.GiftCard;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public interface EmailFormater {
    default Result getResult(GiftCard res) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        String formattedAmount = currencyFormatter.format(res.getAmount());

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", new Locale("es", "CO"));
        String formattedDate = dateFormatter.format(res.getExpirationDate());
        Result result = new Result(formattedAmount, formattedDate);
        return result;
    }

    record Result(String formattedAmount, String formattedDate) {
    }
}
