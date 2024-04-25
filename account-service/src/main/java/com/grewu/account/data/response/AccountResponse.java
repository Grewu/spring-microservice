package com.grewu.account.data.response;

import java.time.LocalDateTime;

public record AccountResponse(
        Long id,
        String name,
        String email,
        String phoneNumber,
        LocalDateTime creationDate
) {
}
