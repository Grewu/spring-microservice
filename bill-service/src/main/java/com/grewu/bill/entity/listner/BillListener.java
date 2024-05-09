package com.grewu.bill.entity.listner;

import com.grewu.bill.entity.Bill;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class BillListener {

    @PrePersist
    public void persist(Bill bill) {
        bill.setCreationDate(LocalDateTime.now());
    }
}
