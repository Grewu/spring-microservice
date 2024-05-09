package com.grewu.bill.service;

import com.grewu.bill.data.request.BillRequest;
import com.grewu.bill.data.response.BillResponse;

import java.util.List;

public interface BillService extends AbstractService<Long, BillRequest, BillResponse> {
    List<BillResponse> getAllBillsAccountId(Long accountId);
}
