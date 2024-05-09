package com.grewu.bill.entity;


import com.grewu.bill.entity.listner.BillListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder(setterPrefix = "set")
@Table(name = "bill")
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@EntityListeners(BillListener.class)
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;
    @Column(name = "account_id", nullable = false)
    private Long accountId;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "is_default", nullable = false, unique = true)
    private Boolean isDefault;
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;
    @Column(name = "overdraft_enabled", nullable = false)
    private Boolean overdraftEnabled;
}
