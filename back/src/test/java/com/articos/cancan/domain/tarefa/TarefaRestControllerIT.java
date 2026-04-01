package com.articos.cancan.domain.tarefa;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.projeto.*;
import com.articos.cancan.domain.tarefa.dto.*;
import com.articos.cancan.utils.*;
import lombok.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;

import java.util.*;

import static com.articos.cancan.domain.projeto.ProjetoDataProvider.*;
import static com.articos.cancan.domain.tarefa.TarefaDataProvider.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TarefaRestControllerIT extends SuperCrudRestControllerIT<
        Tarefa,
        TarefaPayloadDTO,
        TarefaResponseDTO,
        TarefaFiltroDTO,
        TarefaListResponseDTO
        > {

    private static Projeto canCan;

    @Autowired
    private ProjetoRepository projetoRepository;

    @BeforeEach
    public void setup() {
        canCan = projetoRepository.save(projetoCanCan(admin, List.of(member)));
    }

    @Override
    @SneakyThrows
    public void assertAutocompleteResponse(ResultActions result) {
        result.andExpect(jsonPath("$.content[0].id").isNotEmpty())
                .andExpect(jsonPath("$.content[0].descritivo").isNotEmpty());

    }

    @Override
    @SneakyThrows
    public void assertPageableResponse(ResultActions result) {
        result.andExpect(jsonPath("$.content[0].id").isNotEmpty())
                .andExpect(jsonPath("$.content[0].descritivo").isNotEmpty());
    }

    @Override
    @SneakyThrows
    public void assertFindResponse(ResultActions result, Tarefa entity) {
        result.andExpect(jsonPath("$.id").value(entity.getId().toString()))
                .andExpect(jsonPath("$.titulo").value(entity.getTitulo()))
                .andExpect(jsonPath("$.descricao").value(entity.getDescricao()))
                .andExpect(jsonPath("$.status").value(entity.getStatus().name()))
                .andExpect(jsonPath("$.prioridade").value(entity.getPrioridade().name()));
    }

    @Override
    @SneakyThrows
    public void assertFindToEditResponse(ResultActions result, Tarefa entity) {
        result.andExpect(jsonPath("$.id").value(entity.getId().toString()))
                .andExpect(jsonPath("$.titulo").value(entity.getTitulo()))
                .andExpect(jsonPath("$.descricao").value(entity.getDescricao()))
                .andExpect(jsonPath("$.responsavel").value(entity.getResponsavel().getId().toString()))
                .andExpect(jsonPath("$.projeto").value(entity.getProjeto().getId().toString()));
    }

    @Override
    @SneakyThrows
    public void assertCreatedEntity(Tarefa savedEntity, TarefaPayloadDTO sentDto) {
        assertThat(savedEntity.getId()).isNotNull();
        assertThat(savedEntity.getTitulo()).isEqualTo(sentDto.getTitulo());
        assertThat(savedEntity.getDescricao()).isEqualTo(sentDto.getDescricao());
        assertThat(savedEntity.getResponsavel().getId()).isEqualTo(sentDto.getResponsavel());
        assertThat(savedEntity.getProjeto().getId()).isEqualTo(sentDto.getProjeto());
        assertThat(savedEntity.getPrioridade()).isEqualTo(sentDto.getPrioridade());
        assertThat(savedEntity.getStatus()).isEqualTo(sentDto.getStatus());
    }

    @Override
    @SneakyThrows
    public void assertCreateResponse(ResultActions result, TarefaPayloadDTO sentDto) {
        result.andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.titulo").value(sentDto.getTitulo()))
                .andExpect(jsonPath("$.descricao").value(sentDto.getDescricao()))
                .andExpect(jsonPath("$.responsavel").value(sentDto.getResponsavel().toString()))
                .andExpect(jsonPath("$.projeto").value(sentDto.getProjeto().toString()))
                .andExpect(jsonPath("$.prioridade").value(sentDto.getPrioridade().name()))
                .andExpect(jsonPath("$.status").value(sentDto.getStatus().name()));
    }

    @Override
    @SneakyThrows
    public void assertUpdatedEntity(Tarefa updatedEntity, TarefaPayloadDTO sentDto, Tarefa originalEntity) {
        assertThat(updatedEntity.getId()).isEqualTo(originalEntity.getId());
        assertThat(updatedEntity.getTitulo()).isEqualTo(sentDto.getTitulo());
        assertThat(updatedEntity.getDescricao()).isEqualTo(sentDto.getDescricao());
        assertThat(updatedEntity.getResponsavel().getId()).isEqualTo(sentDto.getResponsavel());
        assertThat(updatedEntity.getProjeto().getId()).isEqualTo(sentDto.getProjeto());
        assertThat(updatedEntity.getPrioridade()).isEqualTo(sentDto.getPrioridade());
        assertThat(updatedEntity.getStatus()).isEqualTo(sentDto.getStatus());
    }

    @Override
    @SneakyThrows
    public void assertUpdateResponse(ResultActions result, TarefaPayloadDTO sentDto, Tarefa originalEntity) {
        result.andExpect(jsonPath("$.id").value(originalEntity.getId().toString()))
                .andExpect(jsonPath("$.titulo").value(sentDto.getTitulo()))
                .andExpect(jsonPath("$.descricao").value(sentDto.getDescricao()))
                .andExpect(jsonPath("$.responsavel").value(sentDto.getResponsavel().toString()))
                .andExpect(jsonPath("$.projeto").value(sentDto.getProjeto().toString()))
                .andExpect(jsonPath("$.prioridade").value(sentDto.getPrioridade().name()))
                .andExpect(jsonPath("$.status").value(sentDto.getStatus().name()));
    }

    @Override
    @SneakyThrows
    public void assertDeleteResponse(ResultActions result, Tarefa entity) {
        result.andExpect(jsonPath("$.id").value(entity.getId().toString()))
                .andExpect(jsonPath("$.titulo").value(entity.getTitulo()));
    }

    @Override
    public Tarefa persistValidEntity() {
        TarefaRepository repository1 = (TarefaRepository) repository;
        Integer proximoNumero = repository1.findProximoCodigoByProjeto(canCan.getId());
        return repository1.save(tarefaSpikeCanCan(member, canCan, proximoNumero));
    }

    @Override
    public TarefaPayloadDTO buildValidCreateDto() {
        TarefaPayloadDTO dto = new TarefaPayloadDTO(persistValidEntity());
        ReflectionUtils.setIn(dto, "id", null);
        ReflectionUtils.setIn(dto, "version", null);
        return dto;
    }

    @Override
    public TarefaPayloadDTO buildValidUpdateDto(Tarefa entity) {
        return new TarefaPayloadDTO(entity);
    }

    @Override
    public TarefaFiltroDTO buildValidFilter() {
        return new TarefaFiltroDTO();
    }

    @Override
    public String getBaseUrl() {
        return "/tarefa";
    }

    @Override
    public RequestPostProcessor getJwt() {
        return jwtMember(member.getId());
    }
}
