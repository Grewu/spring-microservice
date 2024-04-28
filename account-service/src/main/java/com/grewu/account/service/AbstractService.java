package com.grewu.account.service;

import org.springframework.data.domain.Page;

public interface AbstractService<ID, Q, R> {

    R getById(ID id);

    Page<R> getAll(Integer size, Integer page);

    R create(Q v);

    R update(Q v);

    void deleteById(ID id);
}
