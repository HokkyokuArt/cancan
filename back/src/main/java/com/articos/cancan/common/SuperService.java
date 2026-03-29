package com.articos.cancan.common;

import lombok.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@RequiredArgsConstructor
public abstract class SuperService<T extends SuperEntity> {
    protected final JpaRepository<T, UUID> repository;

    public T load(UUID id) {
        Optional<T> opt = repository.findById(id);
        return opt.orElse(null);
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
        return opt.get();
    }

    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }
}
