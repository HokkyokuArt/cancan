package com.articos.cancan.common;

import com.articos.cancan.security.jwt.role.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
public abstract class SuperRestController<
        T extends SuperEntity<RESPONSE>,
        PAYLOAD extends SuperPaylodResponeDTO<T>,
        RESPONSE extends SuperPaylodResponeDTO<T>
        > {

    private final SuperService<T> service;

    @MemberAccess
    @GetMapping("find/{id}")
    public ResponseEntity<RESPONSE> find(@PathVariable UUID id) {
        T entity = service.loadWithExcption(id);
        RESPONSE toReturn = entity.toDTO();
        return ResponseEntity.ok(toReturn);
    }

    @MemberAccess
    @PostMapping("create")
    public ResponseEntity<RESPONSE> create(@RequestBody PAYLOAD dto) {
        T entity = convertDTOToEntity(dto);
        T saved = service.save(entity);
        RESPONSE toReturn = saved.toDTO();
        return new ResponseEntity<>(toReturn, HttpStatus.CREATED);
    }

    public T convertDTOToEntity(PAYLOAD dto) {
        return dto.toEntity();
    }
}
