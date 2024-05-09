package com.grewu.bill.controller.open_api;

import com.grewu.bill.data.request.BillRequest;
import com.grewu.bill.data.response.BillResponse;
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

@Tag(name = "Bill")
public interface BillOpenApi {
    @Operation(
            method = "GET",
            tags = "Bill",
            description = "find by id bill",
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
    ResponseEntity<BillResponse> findById(@PathVariable Long id);

    @Operation(
            method = "GET",
            tags = "Bills",
            description = "get all bills",
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
    ResponseEntity<List<BillResponse>> getAll(@RequestParam(required = false) Integer size,
                                              @RequestParam(required = false) Integer page);

    @Operation(
            method = "POST",
            tags = "Bill",
            description = "create bill",
            parameters = {
                    @Parameter(name = "BillRequest", example = "?", required = true)
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
    ResponseEntity<BillResponse> createAccount(@Valid @RequestBody BillRequest billRequest);


    @Operation(
            method = "PUT",
            tags = "Bill",
            description = "update bill",
            parameters = {
                    @Parameter(name = "id", example = "1", required = true),
                    @Parameter(name = "BillRequest", example = "?", required = true)
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
    ResponseEntity<BillResponse> update(@PathVariable Long id, @RequestBody BillRequest billRequest);

    @Operation(
            method = "DELETE",
            tags = "Bill",
            description = "delete bill",
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
