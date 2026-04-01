package com.articos.cancan.common.crud;

import com.articos.cancan.common.*;
import com.articos.cancan.common.interfaces.*;
import com.articos.cancan.configurations.swagger.erros.*;
import com.articos.cancan.security.jwt.role.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import jakarta.validation.*;
import lombok.*;
import org.springdoc.core.annotations.*;
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
    protected final SuperValidator<ENTIDADE, PAYLOAD_DTO> validator;

    @Operation(
            summary = "Autocomplete",
            description = "Retorna uma página simplificada de entidades para uso em autocomplete."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @MemberAccess
    @PostMapping("autocomplete")
    public ResponseEntity<Page<AbstractEntityDTO>> autocomplete(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Filtro utilizado para buscar os registros"
            )
            @RequestBody FILTRO filtro,
            @ParameterObject Pageable pageable
    ) {
        FILTRO novoFiltro = enriquecerFiltro(filtro);
        Page<ENTIDADE> list = service.list(novoFiltro.buildSpecification(), pageable);
        Page<AbstractEntityDTO> toReturn = list.map(AbstractEntityDTO::new);
        return ResponseEntity.ok(toReturn);
    }

    @Operation(
            summary = "Listagem paginada",
            description = "Retorna uma página com os registros filtrados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Página retornada com sucesso")
    })
    @MemberAccess
    @PostMapping("pageable")
    public ResponseEntity<Page<LIST_RESPONSE_DTO>> pageable(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Filtro utilizado para buscar os registros"
            )
            @RequestBody FILTRO filtro,
            @ParameterObject Pageable pageable
    ) {
        FILTRO novoFiltro = enriquecerFiltro(filtro);
        Page<ENTIDADE> list = service.list(novoFiltro.buildSpecification(), pageable);
        Page<LIST_RESPONSE_DTO> toReturn = list.map(DtoConvertible::toListDTO);
        return ResponseEntity.ok(toReturn);
    }

    @Operation(
            summary = "Buscar por ID",
            description = "Retorna o detalhamento completo de um registro."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado"),
    })
    @ApiError404
    @MemberAccess
    @GetMapping("find/{id}")
    public ResponseEntity<RESPONSE_DTO> find(
            @Parameter(description = "ID do registro", required = true)
            @PathVariable UUID id
    ) {
        ENTIDADE entity = service.loadWithException(id, "buscar");
        validator.validateView(entity);
        RESPONSE_DTO toReturn = entity.toResponseDTO();
        return ResponseEntity.ok(toReturn);
    }

    @Operation(
            summary = "Buscar entidades simplificadas por IDs",
            description = "Retorna uma lista simplificada contendo id e descrição das entidades."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @MemberAccess
    @GetMapping("find-abstract-entity")
    public ResponseEntity<List<AbstractEntityDTO>> findAbstractEntity(
            @Parameter(description = "Conjunto de IDs")
            @RequestParam Set<UUID> ids
    ) {
        Map<UUID, ENTIDADE> all = service.findAll(ids);
        List<AbstractEntityDTO> toReturn = all.values().stream().map(AbstractEntityDTO::new).toList();
        return ResponseEntity.ok(toReturn);
    }

    @Operation(
            summary = "Buscar para edição",
            description = "Retorna o payload necessário para edição do registro."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado"),
    })
    @ApiError404
    @MemberAccess
    @GetMapping("find-to-edit/{id}")
    public ResponseEntity<PAYLOAD_DTO> findToEdit(
            @Parameter(description = "ID do registro", required = true)
            @PathVariable UUID id
    ) {
        ENTIDADE entity = service.loadWithException(id, "buscar para editar");
        validator.validateView(entity);
        PAYLOAD_DTO toReturn = entity.toPayloadDTO();
        return ResponseEntity.ok(toReturn);
    }

    @Operation(
            summary = "Criar registro",
            description = "Cria um novo registro."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro criado com sucesso"),
    })
    @ApiError400
    @MemberAccess
    @PostMapping("create")
    public ResponseEntity<PAYLOAD_DTO> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Dados para criação do registro"
            )
            @RequestBody @Valid PAYLOAD_DTO dto
    ) {
        ENTIDADE entity = dto.toEntity();
        updateValues(entity, dto);
        validator.validateCreate(entity, dto);
        ENTIDADE saved = service.save(entity);
        PAYLOAD_DTO toReturn = saved.toPayloadDTO();
        return ResponseEntity.status(HttpStatus.CREATED).body(toReturn);
    }

    @Operation(
            summary = "Atualizar registro",
            description = "Atualiza um registro existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro atualizado com sucesso"),
    })
    @ApiError404
    @ApiError400
    @MemberAccess
    @PutMapping("update")
    public ResponseEntity<PAYLOAD_DTO> update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Dados para atualização do registro"
            )
            @RequestBody @Valid PAYLOAD_DTO dto
    ) {
        ENTIDADE entity = service.loadWithException(dto.getId(), "editar");
        validator.validateUpdate(entity, dto);
        updateValues(entity, dto);
        ENTIDADE saved = service.save(entity);
        PAYLOAD_DTO toReturn = saved.toPayloadDTO();
        return ResponseEntity.ok(toReturn);
    }

    @Operation(
            summary = "Excluir registro",
            description = "Remove um registro existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro removido com sucesso"),
    })
    @ApiError404
    @MemberAccess
    @DeleteMapping("delete/{id}")
    public ResponseEntity<PAYLOAD_DTO> delete(
            @Parameter(description = "ID do registro", required = true)
            @PathVariable UUID id
    ) {
        ENTIDADE entity = service.loadWithException(id, "excluir");
        validator.validateExcluir(entity);
        ENTIDADE deleted = service.delete(entity);
        PAYLOAD_DTO toReturn = deleted.toPayloadDTO();
        return ResponseEntity.ok(toReturn);
    }

    protected FILTRO enriquecerFiltro(FILTRO filtro) {
        return filtro;
    }

    protected abstract void updateValues(ENTIDADE entidade, PAYLOAD_DTO dto);
}