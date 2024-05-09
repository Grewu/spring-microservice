package com.grewu.bill.service.impl;

import com.grewu.bill.data.request.BillRequest;
import com.grewu.bill.data.response.BillResponse;
import com.grewu.bill.entity.Bill;
import com.grewu.bill.exception.NotFoundException;
import com.grewu.bill.mapper.BillMapper;
import com.grewu.bill.repository.BillRepository;
import com.grewu.bill.service.BillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillMapper mapper;

    private final BillRepository billRepository;

    @Override
    public BillResponse getById(Long id) {
        log.info("SERVICE: BILL GET BY ID " + id);
        return billRepository.findById(id).map(mapper::toBillResponse)
                .orElseThrow(() -> NotFoundException.of(Bill.class, id));
    }


    @Override
    public List<BillResponse> getAll(Integer size, Integer page) {
        log.info("SERVICE: BILL PAGE GET ALL");
        return billRepository.findAll(PageRequest.of(size, page))
                .map(mapper::toBillResponse)
                .stream().collect(Collectors.toList());
    }

    @Override
    public BillResponse create(BillRequest billRequest) {
        log.info("SERVICE: BILL CREATE");
        Bill bill = mapper.toBill(billRequest);
        billRepository.save(bill);
        return mapper.toBillResponse(bill);
    }

    @Override
    public BillResponse update(@PathVariable("id") Long id, BillRequest billRequest) {
        log.info("SERVICE: BILL UPDATE");
        return billRepository.findById(id)
                .map(a -> mapper.merge(a, billRequest))
                .map(billRepository::save)
                .map(mapper::toBillResponse)
                .orElseThrow(() -> new NotFoundException("Object type of: by field: null not found."));
    }

    @Override
    public void deleteById(Long id) {
        log.info("SERVICE: BILL DELETE");
        if (id != null && id > 0) {
            billRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
    }

    @Override
    public List<BillResponse> getAllBillsAccountId(Long accountId) {
        log.info("SERVICE: getAllBillsAccountId ");
        return billRepository.getBillsByAccountId(accountId)
                .stream().map(mapper::toBillResponse)
                .collect(Collectors.toList());
    }
}
