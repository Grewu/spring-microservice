package com.grewu.data;


import com.grewu.bill.data.request.BillRequest;
import com.grewu.bill.data.response.BillResponse;
import com.grewu.bill.entity.Bill;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder(setterPrefix = "with")
public class BillTestData {

    @Builder.Default
    private Long billId = 1L;
    @Builder.Default
    private Long accountId = 1L;
    @Builder.Default
    private BigDecimal amount = BigDecimal.TEN;
    @Builder.Default
    private Boolean isDefault = Boolean.TRUE;
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.of(2024, 10, 4, 3, 2);
    @Builder.Default
    private Boolean overdraftEnabled = Boolean.TRUE;

    public Bill buildBill() {
        return new Bill(billId,accountId,amount,isDefault,creationDate,overdraftEnabled);
    }

    public BillResponse buildBillResponse() {
        return new BillResponse(billId,accountId,amount,isDefault,creationDate,overdraftEnabled);
    }

    public BillRequest buildBillRequest() {
        return new BillRequest(accountId,amount,isDefault,creationDate,overdraftEnabled);
    }

    public List<BillResponse> buildListOfBillResponse() {
        return List.of(buildBillResponse());
    }

    public List<Bill> buildListOfBill() {
        return List.of(buildBill());
    }

    public Page<Bill> buildBillPage() {
        return new PageImpl<>(buildListOfBill());
    }
}
