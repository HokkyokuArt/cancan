package com.articos.cancan.common.crud;

import com.articos.cancan.common.exceptions.core.*;
import com.articos.cancan.utils.*;
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
    private Class<?> entityClass;

    {
        this.entityClass = ReflectionUtils.inferGenericType(this.getClass(), 0);
    }

    public Page<T> list(Specification<T> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

    public Map<UUID, T> findAll(Set<UUID> ids) {
        return repository.findAllById(ids)
                .stream()
                .collect(Collectors.toMap(SuperEntity::getId, Function.identity()));
    }

    public T load(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public T loadWithException(UUID id, String fluxo) {
        Optional<T> opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw new EntidadeNaoEncontradaException(entityClass, fluxo);
        }
        return opt.get();
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
