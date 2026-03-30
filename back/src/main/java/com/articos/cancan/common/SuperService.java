package com.articos.cancan.common;

import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@RequiredArgsConstructor
public abstract class SuperService<T extends SuperEntity> {
    protected final SuperRepository<T> repository;

    public Page<T> list(Specification<T> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

    public T load(UUID id) {
        Optional<T> opt = repository.findById(id);
        T toReturn = null;
        if (opt.isPresent()) {
            toReturn = opt.get();
            toReturn.initialize();
        }
        return toReturn;
    }

    public Map<UUID, T> findAll(Set<UUID> ids) {
        return repository.findAllById(ids)
                .stream()
                .collect(Collectors.toMap(SuperEntity::getId, Function.identity()));
    }

    public T loadWithExcption(UUID id) throws RuntimeException {
        Optional<T> opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException("entidade não encontrada");
        }
        T toReturn = opt.get();
        toReturn.initialize();
        return toReturn;
    }

    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Transactional
    public T delete(T entity) {
        repository.delete(entity);
        return entity;
    }
}
