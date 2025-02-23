package com.exito.giftcardmanager.infrastructure.adapter.database.sql.giftcard.data;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "giftcards")
public class GiftCardData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;
    private Double amount;
    private String emailTo;
    private Date creationDate;
    private Date expirationDate;
    private Boolean consumed;

    @PrePersist
    protected void onCreate() {
        if (creationDate == null) {
            creationDate = new Date();
        }
        if (consumed == null) {
            consumed = false;
        }
    }
}