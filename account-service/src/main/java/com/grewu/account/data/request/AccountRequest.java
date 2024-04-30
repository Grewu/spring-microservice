package com.grewu.account.data.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AccountRequest(
        @NotNull
        @Size(min = 2, max = 40)
        String name,
        @Email
        @NotNull
        String email,
        @NotNull
        @Size(min = 2, max = 40)
        String phoneNumber,
        @NotNull
        LocalDateTime creationDate
) {
}
