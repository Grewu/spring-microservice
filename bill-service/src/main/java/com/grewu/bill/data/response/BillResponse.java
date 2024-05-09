package com.grewu.bill.data.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BillResponse(

        Long billId,

        Long accountId,

        BigDecimal amount,

        Boolean isDefault,

        LocalDateTime creationDate,

        Boolean overdraftEnabled
) {
}
