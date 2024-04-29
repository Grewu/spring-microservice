package com.grewu.account.mapper;

import com.grewu.account.data.request.AccountRequest;
import com.grewu.account.data.response.AccountResponse;
import com.grewu.account.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    AccountResponse toAccountResponse(Account account);

    Account toAccount(AccountRequest accountRequest);

    Account merge(@MappingTarget Account account,
               AccountRequest createNewsDto);
}
