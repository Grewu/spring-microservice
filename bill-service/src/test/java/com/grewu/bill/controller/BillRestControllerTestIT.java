package com.grewu.bill.controller;


import com.grewu.bill.service.BillService;
import com.grewu.data.BillTestData;
import com.grewu.utils.IntegrationTest;
import com.grewu.utils.PostgresqlTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@AutoConfigureMockMvc
@Sql("classpath:sql/integration.sql")
@SpringBootTest(properties = {"spring.cloud.config.enabled=false"})
class BillRestControllerTestIT extends PostgresqlTestContainer {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillService service;


    private static final String URL = "/api";
    private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";

    @Test
    void findByIdShouldReturnExpectedResponseEntityBillResponse() throws Exception {
        //given
        var accountId = 1L;
        var requestBuilder = get(URL_WITH_PARAMETER_ID, accountId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "billId": 1,
                          "accountId": 1,
                          "amount": 100.50,
                          "isDefault": true,
                          "creationDate": "2024-05-09T20:30:58.944253",
                          "overdraftEnabled": true
                        }
                            """);
        //when
        mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );

    }

    @Test
    void getAllShouldReturnListOfBillResponse() throws Exception {
        //given
        final String SIZE = "size";
        final String PAGE = "page";

        var size = 0;
        var page = 1;
        var requestBuilder = get(URL)
                .param(SIZE, String.valueOf(size))
                .param(PAGE, String.valueOf(page));

        var expected = BillTestData.builder().build().buildListOfBillResponse();

        doReturn(expected)
                .when(service).getAll(size, page);

        //when
        mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""                             
                                [
                                  {
                                    "billId": 1,
                                    "accountId": 1,
                                    "amount": 10,
                                    "isDefault": true,
                                    "creationDate": "2024-10-04T03:02:00",
                                    "overdraftEnabled": true
                                  }
                                ]
                                 """)
                );
    }

    @Test
    void createShouldReturnBillResponse() throws Exception {
        //given
        var requestBuilder = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "accountId": 1,
                          "amount": 100.50,
                          "isDefault": true,
                          "creationDate": "2024-05-09T12:00:00",
                          "overdraftEnabled": false
                        }
                        """);
        var billRequest = BillTestData.builder().build().buildBillRequest();
        var expected = BillTestData.builder().build().buildBillResponse();

        doReturn(expected)
                .when(service).create(billRequest);

        //when
        mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );

    }

    @Test
    void updateShouldReturnAccountResponse() throws Exception {
        //given
        var billId = 1L;
        var requestBuilder = put(URL_WITH_PARAMETER_ID, billId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "accountId": 1234567890,
                          "amount": 150.75,
                          "isDefault": false,
                          "creationDate": "2024-05-09T15:30:00",
                          "overdraftEnabled": true
                        }
                        """);
        //when
        mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    void deleteById() throws Exception {
        var billId = 1L;
        //when
        mockMvc.perform(delete(URL_WITH_PARAMETER_ID, billId))
                .andExpect(status().isOk());
    }

}
