package com.grewu.account.controller;

import com.grewu.utils.IntegrationTest;
import com.grewu.utils.PostgresqlTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

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


    private static final String URL = "/api";
    private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";

    @Test
    void findByIdShouldReturnExpectedResponseEntityAccountResponse() throws Exception {
        //given
        var accountId = 1L;
        var requestBuilder = get(URL_WITH_PARAMETER_ID, accountId);
        //when
        mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""                            
                                {
                                     "name": "name",
                                     "email": "johndoe@example.com",
                                     "phoneNumber": "phoneNumber",
                                     "creationDate": "2024-10-04T03:02:00"
                                }               
                                """)
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

    //TODO
    @Test
    void createShouldReturnAccountResponse() throws Exception {
        //given
        final String json = """
                {
                  "name": "name",
                  "email": "johndoe@example.com",
                  "phoneNumber": "phoneNumber",
                  "creationDate": "2024-10-04T03:02:00"
                }
                """;
        var requestBuilder = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        //when
        mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );

    }

    //TODO
    @Test
    void updateShouldReturnAccountResponse() throws Exception {
        //given
        String json = """
                {
                  "name": "name",
                  "email": "johndoe@example.com",
                  "phoneNumber": "phoneNumber",
                  "creationDate": "2024-10-04T03:02:00"
                }
                """;
        var requestBuilder = put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
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