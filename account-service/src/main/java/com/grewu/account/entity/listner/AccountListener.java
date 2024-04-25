package com.grewu.account.entity.listner;

import com.grewu.account.entity.Account;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class AccountListener {

    @PrePersist
    public void persist(Account account){
        account.setCreationDate(LocalDateTime.now());
    }
}
