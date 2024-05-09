package com.grewu.bill.service.impl;


import com.grewu.bill.exception.NotFoundException;
import com.grewu.bill.mapper.BillMapper;
import com.grewu.bill.repository.BillRepository;
import com.grewu.bill.service.BillService;
import com.grewu.data.BillTestData;
import com.grewu.utils.IntegrationTest;
import com.grewu.utils.PostgresqlTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@IntegrationTest
@Sql("classpath:sql/integration.sql")
class BillServiceIT extends PostgresqlTestContainer {

    @Autowired
    private BillService service;

    @MockBean
    private BillRepository repository;
    @MockBean
    private BillMapper mapper;


    @Test
    void getByIdShouldReturnExpectedAccountResponse() {
        //given
        var id = 1L;
        final var bill = BillTestData.builder().build().buildBill();
        var expected = BillTestData.builder().build().buildBillResponse();
        //when
        when(repository.findById(id)).thenReturn(Optional.of(bill));
        when(mapper.toBillResponse(bill)).thenReturn(expected);

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
        final var account = BillTestData.builder().build().buildBill();
        final var accountPage = BillTestData.builder().build().buildBillPage();
        final var accountResponse = BillTestData.builder().build().buildBillResponse();
        var expected = BillTestData.builder().build().buildListOfBillResponse();

        when(repository.findAll(PageRequest.of(size, page))).thenReturn(accountPage);
        when(mapper.toBillResponse(account)).thenReturn(accountResponse);
        //when
        var actual = service.getAll(size, page);
        //then
        assertThat(actual)
                .hasSameElementsAs(expected);
    }

    @Test
    void createShouldReturnAccountResponse() {
        //given
        var accountRequest = BillTestData.builder().build().buildBillRequest();
        var expected = BillTestData.builder().build().buildBillResponse();
        var account = BillTestData.builder().build().buildBill();
        final var accountResponse = BillTestData.builder().build().buildBillResponse();

        //when
        when(mapper.toBillResponse(account)).thenReturn(accountResponse);
        when(mapper.toBill(accountRequest)).thenReturn(account);

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
        var accountRequest = BillTestData.builder().build().buildBillRequest();
        var expected = BillTestData.builder().build().buildBillResponse();
        final var accountResponse = BillTestData.builder().build().buildBillResponse();
        final var account = BillTestData.builder().build().buildBill();
        final var id = 1L;
        when(repository.findById(1L)).thenReturn(Optional.of(account));
        when(mapper.merge(account, accountRequest)).thenReturn(account);
        when(repository.save(account)).thenReturn(account);
        when(mapper.toBillResponse(account)).thenReturn(accountResponse);
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