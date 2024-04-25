package com.grewu.account.service;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AbstractService<ID, Q, R> {

    R getById(ID id);

    List<R> getAll();

    Page<R> getAll(Integer size, Integer page);

    R create(Q v);

    R update(Q v);

    void deleteById(ID id);
}
