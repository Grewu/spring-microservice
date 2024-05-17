package com.grewu.data;

import com.grewu.account.data.request.AccountRequest;
import com.grewu.account.data.response.AccountResponse;
import com.grewu.account.entity.Account;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;

@Builder(setterPrefix = "with")
public class AccountTestData {

    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String name = "name";
    @Builder.Default
    private String email = "johndoe@example.com";
    @Builder.Default
    private String phoneNumber = "phoneNumber";
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.of(2024, 10, 4, 3, 2);
    @Builder.Default
    private List<Long> bills = List.of(1L);

    public Account buildAccount() {
        return new Account(id, name, email, phoneNumber, creationDate);
    }

    public AccountResponse buildAccountResponse() {
        return new AccountResponse(name, email, phoneNumber, creationDate);
    }

    public AccountRequest buildAccountRequest() {
        return new AccountRequest(name, email, phoneNumber, creationDate);
    }

    public List<AccountResponse> buildListOfAccountResponse() {
        return List.of(buildAccountResponse());
    }

    public List<Account> buildListOfAccount() {
        return List.of(buildAccount());
    }

    public Page<Account> buildAccountPage() {
        return new PageImpl<>(buildListOfAccount());
    }
}
