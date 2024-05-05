package com.grewu.account.service.impl;

import com.grewu.account.exception.NotFoundException;
import com.grewu.account.mapper.AccountMapper;
import com.grewu.account.repository.AccountRepository;
import com.grewu.account.service.AccountService;
import com.grewu.data.AccountTestData;
import com.grewu.utils.IntegrationTest;
import com.grewu.utils.PostgresqlTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@IntegrationTest
@Sql("classpath:sql/integration.sql")
class AccountServiceIT extends PostgresqlTestContainer {

    @Autowired
    private AccountService service;

    @MockBean
    private AccountRepository repository;
    @MockBean
    private AccountMapper mapper;


    @Test
    void getByIdShouldReturnExpectedAccountResponse() {
        //given
        var id = 1L;
        final var account = AccountTestData.builder().build().buildAccount();
        var expected = AccountTestData.builder().build().buildAccountResponse();
        //when
        when(repository.findById(id)).thenReturn(Optional.of(account));
        when(mapper.toAccountResponse(account)).thenReturn(expected);

        var actual = service.getById(id);
        //then
        assertEquals(expected, actual);
    }


    @Test
    void getByIdShouldNotFoundException() {
        //given
        var id = -1L;
        final var expected = "not found";
        //when
        var exception = assertThrows(NotFoundException.class,
                () -> service.getById(id));
        String actual = exception.getMessage();
        //then
        assertThat(actual)
                .contains(expected);
    }


    @Test
    void getAllShouldReturnListOfAccountResponse() {
        var size = 0;
        var page = 1;
        final var account = AccountTestData.builder().build().buildAccount();
        final var accountPage = AccountTestData.builder().build().buildAccountPage();
        final var accountResponse = AccountTestData.builder().build().buildAccountResponse();
        var expected = AccountTestData.builder().build().buildListOfAccountResponse();

        when(repository.findAll(PageRequest.of(size, page))).thenReturn(accountPage);
        when(mapper.toAccountResponse(account)).thenReturn(accountResponse);
        //when
        var actual = service.getAll(size, page);
        //then
        assertThat(actual)
                .hasSameElementsAs(expected);
    }

    @Test
    void createShouldReturnAccountResponse() {
        //given
        var accountRequest = AccountTestData.builder().build().buildAccountRequest();
        var expected = AccountTestData.builder().build().buildAccountResponse();
        var account = AccountTestData.builder().build().buildAccount();
        final var accountResponse = AccountTestData.builder().build().buildAccountResponse();

        //when
        when(mapper.toAccountResponse(account)).thenReturn(accountResponse);
        when(mapper.toAccount(accountRequest)).thenReturn(account);

        var actual = service.create(accountRequest);

        verify(repository).save(account);
        //then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void updateShouldReturnAccountResponse() {
        //given
        var accountRequest = AccountTestData.builder().build().buildAccountRequest();
        var expected = AccountTestData.builder().build().buildAccountResponse();
        final var accountResponse = AccountTestData.builder().build().buildAccountResponse();
        final var account = AccountTestData.builder().build().buildAccount();
        final var id = 1L;
        when(repository.findById(1L)).thenReturn(Optional.of(account));
        when(mapper.merge(account, accountRequest)).thenReturn(account);
        when(repository.save(account)).thenReturn(account);
        when(mapper.toAccountResponse(account)).thenReturn(accountResponse);
        //when
        var actual = service.update(id, accountRequest);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void deleteById() {
        //given
        var id = 1L;
        //when
        service.deleteById(id);
        //then
        verify(repository).deleteById(id);
    }

    @Test
    void deleteByIdShouldReturnIllegalArgumentException() {
        //given
        var fakeId = -1L;
        final var expected = "Invalid id";
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.deleteById(fakeId));
        String actual = exception.getMessage();
        //then
        assertThat(actual)
                .contains(expected);
    }

    @Test
    void deleteByIdShouldReturnIllegalArgumentExceptionWhenIdIsNull() {
        //given
        final var expected = "Invalid id";
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.deleteById(null));
        String actual = exception.getMessage();
        //then
        assertThat(actual)
                .contains(expected);
    }
}