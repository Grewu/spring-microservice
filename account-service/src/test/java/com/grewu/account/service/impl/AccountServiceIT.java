package com.grewu.account.service.impl;

import com.grewu.account.data.response.AccountResponse;
import com.grewu.account.exception.NotFoundException;
import com.grewu.account.repository.AccountRepository;
import com.grewu.account.service.AccountService;
import com.grewu.data.AccountTestData;
import com.grewu.utils.IntegrationTest;
import com.grewu.utils.PostgresqlTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@IntegrationTest
@Sql("classpath:sql/integration.sql")
class AccountServiceIT extends PostgresqlTestContainer {

    @Autowired
    private AccountService service;

    @Autowired
    private AccountRepository repository;

    @Test
    void getByIdShouldReturnExpectedAccountResponse() {
        //given
        var id = 1L;
        var expected = AccountTestData.builder().build().buildAccountResponse();
        //when
        AccountResponse actual = service.getById(id);
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
                () -> service.getById(id));
        String actual = exception.getMessage();
        //then
        assertThat(actual)
                .contains(expected);
    }


    @Test
    void getAllShouldReturnPageOfAccountResponse() {
        var size = 0;
        var page = 1;
        Page<AccountResponse> expected = AccountTestData.builder().build().buildAccountResponsePage();
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
        //when
        var actual = service.create(accountRequest);
        //then
        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void updateShouldReturnAccountResponse() {
        //given
        var accountRequest = AccountTestData.builder().build().buildAccountRequest();
        var expected = AccountTestData.builder().build().buildAccountResponse();
        //when
        var actual = service.update(accountRequest);
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
        repository.deleteById(id);
    }

    @Test
    void deleteByIdShouldReturnIllegalArgumentException() {
        //given
        var fakeId = -1L;
        String expected = "Invalid id";
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
        String expected = "Invalid id";
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.deleteById(null));
        String actual = exception.getMessage();
        //then
        assertThat(actual)
                .contains(expected);
    }
}