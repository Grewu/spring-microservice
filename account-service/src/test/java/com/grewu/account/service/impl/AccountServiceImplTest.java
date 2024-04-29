package com.grewu.account.service.impl;


import com.grewu.account.exception.NotFoundException;
import com.grewu.account.mapper.AccountMapper;
import com.grewu.account.repository.AccountRepository;
import com.grewu.data.AccountTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        final var id = 1L;
        final var account = AccountTestData.builder().build().buildAccount();
        final var expected = AccountTestData.builder().build().buildAccountResponse();
        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        when(mapper.toAccountResponse(account)).thenReturn(expected);
        //when
        final var actual = accountService.getById(id);

        //then
        assertEquals(expected, actual);
    }


    @Test
    void getByIdShouldNotFoundException() {
        //given
        final var id = -1L;
        final var EXPECTED_NOT_FOUND = "not found";
        //when
        final var exception = assertThrows(NotFoundException.class,
                () -> accountService.getById(id));
        String actual = exception.getMessage();
        //then
        assertThat(actual)
                .contains(EXPECTED_NOT_FOUND);
    }

    @Test
    void getAllShouldReturnExpectedPageOfAccountResponse() {
        //given
        final var size = 15;
        final var page = 1;
        final var accountPage = AccountTestData.builder().build().buildAccountPage();
        final var expected = AccountTestData.builder().build().buildAccountResponsePage();
        final var account = AccountTestData.builder().build().buildAccount();
        final var accountResponse = AccountTestData.builder().build().buildAccountResponse();

        when(accountRepository.findAll(PageRequest.of(size, page))).thenReturn(accountPage);
        when(mapper.toAccountResponse(account)).thenReturn(accountResponse);
        //when
        final var actual = accountService.getAll(size, page);
        //then
        assertThat(actual)
                .hasSameElementsAs(expected);
    }


    @Test
    void createShouldReturnAccountResponse() {
        //given
        final var accountRequest = AccountTestData.builder().build().buildAccountRequest();
        final var account = AccountTestData.builder().build().buildAccount();
        final var accountResponse = AccountTestData.builder().build().buildAccountResponse();
        final var expected = AccountTestData.builder().build().buildAccountResponse();

        when(mapper.toAccountResponse(account)).thenReturn(accountResponse);
        when(mapper.toAccount(accountRequest)).thenReturn(account);
        //when
        final var actual = accountService.create(accountRequest);
        //then
        verify(accountRepository).save(account);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void updateShouldReturnAccountResponse() {
        //given
        final var accountRequest = AccountTestData.builder().build().buildAccountRequest();
        final var accountResponse = AccountTestData.builder().build().buildAccountResponse();
        final var account = AccountTestData.builder().build().buildAccount();
        final var expected = AccountTestData.builder().build().buildAccountResponse();
        //when
        when(accountRepository.findById(accountRequest.id())).thenReturn(Optional.of(account));
        when(mapper.merge(account, accountRequest)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(mapper.toAccountResponse(account)).thenReturn(accountResponse);

        var actual = accountService.update(accountRequest);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void updateShouldThrowNotFoundException() {
        //given
        final var accountRequest = AccountTestData.builder()
                .withId(null)
                .build()
                .buildAccountRequest();
        //when
        var exception = assertThrows(NotFoundException.class, () -> accountService.update(accountRequest));
        var actual = exception.getMessage();
        //then
        final var NULL_NOT_FOUND = "Object type of: by field: null not found.";
        assertThat(actual)
                .contains(NULL_NOT_FOUND);
    }

    @Test
    void deleteById() {
        //given
        final var id = 1L;
        //when
        accountService.deleteById(id);
        //then
        verify(accountRepository).deleteById(id);
    }

    @Test
    void deleteByIdShouldReturnIllegalArgumentException() {
        //given
        final var fakeId = -1L;
        //when
        var exception = assertThrows(IllegalArgumentException.class, () -> accountService.deleteById(fakeId));
        var actual = exception.getMessage();
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