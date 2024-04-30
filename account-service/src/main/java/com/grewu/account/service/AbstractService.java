package com.grewu.account.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AbstractService<ID, Q, R> {

    R getById(ID id);

    List<R> getAll(Integer size, Integer page);

    R create(Q v);

    R update(ID id,Q v);

    void deleteById(ID id);
}
