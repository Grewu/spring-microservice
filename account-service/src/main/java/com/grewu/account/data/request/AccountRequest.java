package com.grewu.account.data.request;

import java.time.LocalDateTime;

public record AccountRequest(
        Long id,
        String name,
        String email,
        String phoneNumber,
        LocalDateTime creationDate
) {
}
