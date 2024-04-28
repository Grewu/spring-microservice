package com.grewu.account.service.impl;


import com.grewu.account.data.response.AccountResponse;
import com.grewu.account.entity.Account;
import com.grewu.account.exception.NotFoundException;
import com.grewu.account.mapper.AccountMapper;
import com.grewu.account.repository.AccountRepository;
import com.grewu.data.AccountTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountMapper mapper;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private final String INVALID_ID = "Invalid id";

    @Test
    void getByIdShouldReturnExpectedAccountResponse() {
        //given
        var id = 1L;
        var account = AccountTestData.builder().build().buildAccount();
        var expected = AccountTestData.builder().build().buildAccountResponse();
        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        when(mapper.toAccountResponse(account)).thenReturn(expected);
        //when
        var actual = accountService.getById(id);

        //then
        assertEquals(expected, actual);
    }


    @Test
    void getByIdShouldNotFoundException() {
        //given
        var id = -1L;
        String expected = "not found";
        //when
        var exception = assertThrows(NotFoundException.class,
                () -> accountService.getById(id));
        String actual = exception.getMessage();
        //then
        assertThat(actual)
                .contains(expected);
    }

    @Test
    void getAllShouldReturnExpectedPageOfAccountResponse() {
        //given
        var size = 15;
        var page = 1;
        Page<Account> accountPage = AccountTestData.builder().build().buildAccountPage();
        Page<AccountResponse> expected = AccountTestData.builder().build().buildAccountResponsePage();
        var account = AccountTestData.builder().build().buildAccount();
        var accountResponse = AccountTestData.builder().build().buildAccountResponse();

        when(accountRepository.findAll(PageRequest.of(size, page))).thenReturn(accountPage);
        when(mapper.toAccountResponse(account)).thenReturn(accountResponse);
        //when
        var actual = accountService.getAll(size, page);
        //then
        assertThat(actual)
                .hasSameElementsAs(expected);
    }


    @Test
    void createShouldReturnAccountResponse() {
        //given
        var accountRequest = AccountTestData.builder().build().buildAccountRequest();
        var account = AccountTestData.builder().build().buildAccount();
        var accountResponse = AccountTestData.builder().build().buildAccountResponse();
        var expected = AccountTestData.builder().build().buildAccountResponse();

        when(mapper.toAccount(accountRequest)).thenReturn(account);
        when(mapper.toAccountResponse(account)).thenReturn(accountResponse);

        //when
        var actual = accountService.create(accountRequest);
        //then
        verify(accountRepository).save(account);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void updateShouldReturnAccountResponse() {
        //given
        var accountRequest = AccountTestData.builder().build().buildAccountRequest();
        var accountResponse = AccountTestData.builder().build().buildAccountResponse();
        var account = AccountTestData.builder().build().buildAccount();
        var expected = AccountTestData.builder().build().buildAccountResponse();
        //when
        when(accountRepository.save(account)).thenReturn(account);
        when(mapper.toAccountResponse(account)).thenReturn(accountResponse);

        var actual = accountService.update(accountRequest);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void deleteById() {
        //given
        var id = 1L;
        //when
        accountService.deleteById(id);
        //then
        verify(accountRepository).deleteById(id);
    }

    @Test
    void deleteByIdShouldReturnIllegalArgumentException() {
        //given
        var fakeId = -1L;
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> accountService.deleteById(fakeId));
        String actual = exception.getMessage();
        //then
        assertThat(actual)
                .contains(INVALID_ID);
    }

    @Test
    void deleteByIdShouldReturnIllegalArgumentExceptionWhenIdIsNull() {
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> accountService.deleteById(null));
        String actual = exception.getMessage();
        //then
        assertThat(actual)
                .contains(INVALID_ID);
    }
}