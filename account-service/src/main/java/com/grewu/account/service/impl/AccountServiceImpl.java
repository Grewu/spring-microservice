package com.grewu.account.service.impl;

import com.grewu.account.data.request.AccountRequest;
import com.grewu.account.data.response.AccountResponse;
import com.grewu.account.entity.Account;
import com.grewu.account.exception.NotFoundException;
import com.grewu.account.mapper.AccountMapper;
import com.grewu.account.repository.AccountRepository;
import com.grewu.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountMapper mapper;

    private final AccountRepository accountRepository;

    @Override
    public AccountResponse getById(Long id) {
        log.info("SERVICE: ACCOUNT GET BY ID " + id);
        return accountRepository.findById(id).map(mapper::toAccountResponse)
                .orElseThrow(() -> NotFoundException.of(Account.class, id));
    }


    @Override
    public Page<AccountResponse> getAll(Integer size, Integer page) {
        log.info("SERVICE: ACCOUNT PAGE GET ALL");
        return accountRepository.findAll(PageRequest.of(size, page))
                .map(mapper::toAccountResponse);
    }

    @Override
    public AccountResponse create(AccountRequest accountRequest) {
        log.info("SERVICE: ACCOUNT CREATE");
        Account account = mapper.toAccount(accountRequest);
        accountRepository.save(account);
        return mapper.toAccountResponse(account);
    }

    @Override
    public AccountResponse update(AccountRequest accountRequest) {
       return accountRepository.findById(accountRequest.id())
               .map(a -> mapper.merge(a,accountRequest))
               .map(accountRepository::save)
               .map(mapper::toAccountResponse)
               .orElseThrow(() -> new NotFoundException("Object type of: by field: null not found."));

    }

    @Override
    public void deleteById(Long id) {
        if (id != null && id > 0) {
            accountRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
    }
}
