package com.grewu.bill.controller;

import com.grewu.bill.data.request.BillRequest;
import com.grewu.bill.data.response.BillResponse;
import com.grewu.bill.service.BillService;
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
public class BillRestController {

    private final BillService service;

    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getById(id));
    }

    //TODO:Pageable
    @GetMapping
    public ResponseEntity<List<BillResponse>> getAll(@RequestParam(required = false, defaultValue = "0") Integer size,
                                                     @RequestParam(required = false, defaultValue = "1") Integer page) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getAll(size, page));
    }

    @PostMapping
    public ResponseEntity<BillResponse> createBill(@Valid @RequestBody BillRequest billRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.create(billRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillResponse> update(@PathVariable Long id, @RequestBody BillRequest billRequest) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .location(URI.create("/bills/" + id))
                .body(service.update(id, billRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok()
                .build();
    }

}
