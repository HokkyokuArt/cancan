package com.articos.cancan.common;

import com.articos.cancan.security.jwt.role.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
public abstract class SuperRestController<
        ENTIDADE extends SuperEntity<PAYLOAD_DTO, RESPONSE_DTO, LIST_RESPONSE_DTO>,
        PAYLOAD_DTO extends SuperPayloadDTO<ENTIDADE>,
        RESPONSE_DTO extends SuperResponseDTO<ENTIDADE>,
        FILTRO extends Filtro<ENTIDADE>,
        LIST_RESPONSE_DTO extends AbstractEntityDTO
        > {

    protected final SuperService<ENTIDADE> service;
    protected final SuperValidator<ENTIDADE> validator;

    @MemberAccess
    @PostMapping("pageable")
    public ResponseEntity<Page<LIST_RESPONSE_DTO>> pageable(@RequestBody FILTRO filtro, Pageable pageable) {
        Page<ENTIDADE> list = service.list(filtro.buildSpecification(), pageable);
        Page<LIST_RESPONSE_DTO> toReturn = list.map(DtoConvertible::toListDTO);
        return ResponseEntity.ok(toReturn);
    }

    @MemberAccess
    @GetMapping("find/{id}")
    public ResponseEntity<RESPONSE_DTO> find(@PathVariable UUID id) {
        ENTIDADE entity = service.loadWithExcption(id);
        RESPONSE_DTO toReturn = entity.toDTO();
        return ResponseEntity.ok(toReturn);
    }

    @MemberAccess
    @PostMapping("create")
    public ResponseEntity<RESPONSE_DTO> create(@RequestBody @Valid PAYLOAD_DTO dto) throws Exception {
        ENTIDADE entity = dto.toEntity();
        updateValues(entity, dto);
        validator.validate(entity);
        ENTIDADE saved = service.save(entity);
        RESPONSE_DTO toReturn = saved.toDTO();
        return new ResponseEntity<>(toReturn, HttpStatus.CREATED);
    }

    @MemberAccess
    @PutMapping("update")
    public ResponseEntity<RESPONSE_DTO> update(@RequestBody @Valid PAYLOAD_DTO dto) throws Exception {
        ENTIDADE entity = service.loadWithExcption(dto.getId()); // jogar pra dentro do service pra não precisar do load?
        updateValues(entity, dto);
        validator.validate(entity);
        ENTIDADE saved = service.save(entity);
        RESPONSE_DTO toReturn = saved.toDTO();
        return new ResponseEntity<>(toReturn, HttpStatus.CREATED);
    }

    @MemberAccess
    @DeleteMapping("delete/{id}")
    public ResponseEntity<RESPONSE_DTO> delete(@PathVariable UUID id) throws Exception {
        ENTIDADE entity = service.loadWithExcption(id);
        validator.validateExcluir(entity);
        ENTIDADE deleted = service.delete(entity);
        RESPONSE_DTO toReturn = deleted.toDTO();
        return ResponseEntity.ok(toReturn);
    }

    protected void updateValues(ENTIDADE entidade, PAYLOAD_DTO dto) {
        entidade.setValues(dto);
    }
}
