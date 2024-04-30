package com.grewu.account.controller;

import com.grewu.account.data.request.AccountRequest;
import com.grewu.account.data.response.AccountResponse;
import com.grewu.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountRestController {

    private final AccountService service;

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getById(id));
    }


    //TODO:Test
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAll(@RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) Integer page) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getAll(size, page));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest accountRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(accountRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> update(@PathVariable Long id, @RequestBody AccountRequest accountRequest) {
        return ResponseEntity.ok()
                .location(URI.create("/accounts/" + id))
                .body(service.update(id, accountRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
