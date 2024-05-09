package com.grewu.bill.mapper;

import com.grewu.bill.data.request.BillRequest;
import com.grewu.bill.data.response.BillResponse;
import com.grewu.bill.entity.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BillMapper {

    BillResponse toBillResponse(Bill bill);

    Bill toBill(BillRequest billRequest);

    Bill merge(@MappingTarget Bill bill, BillRequest billRequest);
}
