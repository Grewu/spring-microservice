package com.grewu.bill.service.impl;


import com.grewu.bill.exception.NotFoundException;
import com.grewu.bill.mapper.BillMapper;
import com.grewu.bill.repository.BillRepository;
import com.grewu.data.BillTestData;
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
class BillServiceImplTest {

    @Mock
    private BillMapper mapper;

    @Mock
    private BillRepository billRepository;

    @InjectMocks
    private BillServiceImpl billService;

    private final String INVALID_ID = "Invalid id";

    @Test
    void getByIdShouldReturnExpectedAccountResponse() {
        //given
        final var id = 1L;
        final var account = BillTestData.builder().build().buildBill();
        final var expected = BillTestData.builder().build().buildBillResponse();
        when(billRepository.findById(id)).thenReturn(Optional.of(account));
        when(mapper.toBillResponse(account)).thenReturn(expected);
        //when
        final var actual = billService.getById(id);

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
                () -> billService.getById(id));
        String actual = exception.getMessage();
        //then
        assertThat(actual)
                .contains(EXPECTED_NOT_FOUND);
    }

    @Test
    void getAllShouldReturnExpectedListOfAccountResponse() {
        //given
        final var size = 15;
        final var page = 1;
        final var billPage = BillTestData.builder().build().buildBillPage();
        final var expected = BillTestData.builder().build().buildListOfBillResponse();
        final var bill = BillTestData.builder().build().buildBill();
        final var billResponse = BillTestData.builder().build().buildBillResponse();

        when(billRepository.findAll(PageRequest.of(size, page))).thenReturn(billPage);
        when(mapper.toBillResponse(bill)).thenReturn(billResponse);
        //when
        final var actual = billService.getAll(size, page);
        //then
        assertThat(actual)
                .hasSameElementsAs(expected);
    }


    @Test
    void createShouldReturnAccountResponse() {
        //given
        final var billRequest = BillTestData.builder().build().buildBillRequest();
        final var bill = BillTestData.builder().build().buildBill();
        final var billResponse = BillTestData.builder().build().buildBillResponse();
        final var expected = BillTestData.builder().build().buildBillResponse();

        when(mapper.toBillResponse(bill)).thenReturn(billResponse);
        when(mapper.toBill(billRequest)).thenReturn(bill);
        //when
        final var actual = billService.create(billRequest);
        //then
        verify(billRepository).save(bill);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void updateShouldReturnAccountResponse() {
        //given
        final var billRequest = BillTestData.builder().build().buildBillRequest();
        final var billResponse = BillTestData.builder().build().buildBillResponse();
        final var bill = BillTestData.builder().build().buildBill();
        final var expected = BillTestData.builder().build().buildBillResponse();
        //when
        when(billRepository.findById(1L)).thenReturn(Optional.of(bill));
        when(mapper.merge(bill, billRequest)).thenReturn(bill);
        when(billRepository.save(bill)).thenReturn(bill);
        when(mapper.toBillResponse(bill)).thenReturn(billResponse);

        var actual = billService.update(1L, billRequest);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void updateShouldThrowNotFoundException() {
        //given
        final var billRequest = BillTestData.builder()
                .build()
                .buildBillRequest();
        final var id = -1L;
        //when
        final var exception = assertThrows(NotFoundException.class, () -> billService.update(id, billRequest));
        final var actual = exception.getMessage();
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
        billService.deleteById(id);
        //then
        verify(billRepository).deleteById(id);
    }

    @Test
    void deleteByIdShouldReturnIllegalArgumentException() {
        //given
        final var fakeId = -1L;
        //when
        var exception = assertThrows(IllegalArgumentException.class, () -> billService.deleteById(fakeId));
        var actual = exception.getMessage();
        //then
        assertThat(actual)
                .contains(INVALID_ID);
    }

    @Test
    void deleteByIdShouldReturnIllegalArgumentExceptionWhenIdIsNull() {
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> billService.deleteById(null));
        String actual = exception.getMessage();
        //then
        assertThat(actual)
                .contains(INVALID_ID);
    }
}