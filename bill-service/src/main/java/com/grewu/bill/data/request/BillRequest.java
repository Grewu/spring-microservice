package com.grewu.bill.data.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BillRequest(

        @NotNull
        Long accountId,
        @NotNull
        BigDecimal amount,
        @NotNull
        Boolean isDefault,
        @NotNull
        LocalDateTime creationDate,
        @NotNull
        Boolean overdraftEnabled
) {
}
