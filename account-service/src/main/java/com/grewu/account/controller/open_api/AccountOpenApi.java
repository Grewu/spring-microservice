package com.grewu.account.controller.open_api;

import com.grewu.account.data.request.AccountRequest;
import com.grewu.account.data.response.AccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "User")
public interface AccountOpenApi {
    @Operation(
            method = "GET",
            tags = "Account",
            description = "find by id account",
            parameters = {
                    @Parameter(name = "ID", example = "1", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    examples = @ExampleObject("""
                                             {
                                                "name": "name",
                                                "email": "johndoe@example.com",
                                                "phoneNumber": "phoneNumber",
                                                "creationDate": "2024-10-04T03:02:00"
                                            }
                                             """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    examples = @ExampleObject("""
                                            {
                                                "status": 400,
                                                "message": "Error?"
                                            }
                                            """)
                            )
                    )
            }
    )
    ResponseEntity<AccountResponse> findById(@PathVariable Long id);

    @Operation(
            method = "GET",
            tags = "Accounts",
            description = "get all accounts",
            parameters = {
                    @Parameter(name = "size", example = "0", required = true),
                    @Parameter(name = "page", example = "1", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    examples = @ExampleObject("""
                                            [
                                                 {
                                                  "name": "name",
                                                  "email": "johndoe@example.com",
                                                  "phoneNumber": "phoneNumber",
                                                  "creationDate": "2024-10-04T03:02:00"
                                                 }
                                             ]
                                                          """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    examples = @ExampleObject("""
                                            {
                                                "status": 400,
                                                "message": "Error?"
                                            }
                                            """)
                            )
                    )
            }
    )
    ResponseEntity<List<AccountResponse>> getAll(@RequestParam(required = false) Integer size,
                                                 @RequestParam(required = false) Integer page);

    @Operation(
            method = "POST",
            tags = "Account",
            description = "create account",
            parameters = {
                    @Parameter(name = "AccountRequest", example = "?", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    examples = @ExampleObject("""
                                            {
                                              "name": "user",
                                              "email": "johndoe@example.com",
                                              "phoneNumber": "phoneNumber",
                                              "creationDate": "2024-10-04T03:02:00"
                                            }
                                                                              """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    examples = @ExampleObject("""
                                            {
                                                "status": 400,
                                                "message": "Error?"
                                            }
                                            """)
                            )
                    )
            }
    )
    ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest accountRequest);


    @Operation(
            method = "PUT",
            tags = "Account",
            description = "update account",
            parameters = {
                    @Parameter(name = "id", example = "1", required = true),
                    @Parameter(name = "AccountRequest", example = "?", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    examples = @ExampleObject("""
                                            {
                                              "name": "user",
                                              "email": "johndoe@example.com",
                                              "phoneNumber": "phoneNumber",
                                              "creationDate": "2024-10-04T03:02:00"
                                            }
                                                                              """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    examples = @ExampleObject("""
                                            {
                                                "status": 400,
                                                "message": "Error?"
                                            }
                                            """)
                            )
                    )
            }
    )
    ResponseEntity<AccountResponse> update(@PathVariable Long id, @RequestBody AccountRequest accountRequest);

    @Operation(
            method = "DELETE",
            tags = "Account",
            description = "delete account",
            parameters = {
                    @Parameter(name = "id", example = "1", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    examples = @ExampleObject("""
                                            {
                                                "status": 400,
                                                "message": "Error?"
                                            }
                                            """)
                            )
                    )
            }
    )
    ResponseEntity<Void> delete(@PathVariable Long id);


}
