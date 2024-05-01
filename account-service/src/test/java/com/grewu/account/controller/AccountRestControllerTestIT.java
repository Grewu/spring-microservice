package com.grewu.account.controller;

import com.grewu.account.service.AccountService;
import com.grewu.data.AccountTestData;
import com.grewu.utils.IntegrationTest;
import com.grewu.utils.PostgresqlTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
class AccountRestControllerTestIT extends PostgresqlTestContainer {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService service;


    private static final String URL = "/api";
    private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";

    @Test
    void findByIdShouldReturnExpectedResponseEntityAccountResponse() throws Exception {
        //given
        var accountId = 1L;
        var requestBuilder = get(URL_WITH_PARAMETER_ID, accountId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "name",
                            "email": "johndoe@example.com",
                            "phoneNumber": "phoneNumber",
                            "creationDate": "2024-10-04T03:02:00"
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
    void getAllShouldReturnListOfAccountResponse() throws Exception {
        //given
        final String SIZE = "size";
        final String PAGE = "page";

        var size = 0;
        var page = 1;
        var requestBuilder = get(URL)
                .param(SIZE, String.valueOf(size))
                .param(PAGE, String.valueOf(page));

        var expected = AccountTestData.builder().build().buildListOfAccountResponse();

        doReturn(expected)
                .when(service).getAll(size,page);

        //when
        mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""                             
                                [
                                  {
                                    "name": "name",
                                    "email": "johndoe@example.com",
                                    "phoneNumber": "phoneNumber",
                                    "creationDate": "2024-10-04T03:02:00"
                                  }
                                ]
                                 """)
                );
    }

    @Test
    void createShouldReturnAccountResponse() throws Exception {
        //given
        var requestBuilder = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "user",
                          "email": "johndoe@example.com",
                          "phoneNumber": "phoneNumber",
                          "creationDate": "2024-10-04T03:02:00"
                        }
                        """);
        var accountRequest = AccountTestData.builder().build().buildAccountRequest();
        var expected = AccountTestData.builder().build().buildAccountResponse();

        doReturn(expected)
                .when(service).create(accountRequest);

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
        var accountId = 1L;
        var requestBuilder = put(URL_WITH_PARAMETER_ID, accountId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "name",
                          "email": "johndoe@example.com",
                          "phoneNumber": "phoneNumber",
                          "creationDate": "2024-10-04T03:02:00"
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
        var accountId = 1L;
        //when
        mockMvc.perform(delete(URL_WITH_PARAMETER_ID, accountId))
                .andExpect(status().isOk());
    }

}