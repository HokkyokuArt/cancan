package com.articos.cancan.domain.projeto;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.projeto.dto.*;
import com.articos.cancan.utils.*;
import lombok.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;

import java.util.*;

import static com.articos.cancan.domain.projeto.ProjetoDataProvider.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProjetoRestControllerIT extends SuperCrudRestControllerIT<
        Projeto,
        ProjetoPayloadDTO,
        ProjetoPayloadDTO,
        ProjetoFiltroDTO,
        ProjetoListResponsavelDTO
        > {

    @Override
    @SneakyThrows
    public void assertFindResponse(ResultActions result, Projeto entity) {
        result.andExpect(jsonPath("$.sigla").value(entity.getSigla()))
                .andExpect(jsonPath("$.descricao").value(entity.getDescricao()))
                .andExpect(jsonPath("$.dono").value(entity.getDono().getId().toString()))
                .andExpect(jsonPath("$.membros.size()").value(1));
    }

    @Override
    @SneakyThrows
    public void assertFindToEditResponse(ResultActions result, Projeto entity) {
        result.andExpect(jsonPath("$.sigla").value(entity.getSigla()))
                .andExpect(jsonPath("$.descricao").value(entity.getDescricao()))
                .andExpect(jsonPath("$.dono").value(entity.getDono().getId().toString()))
                .andExpect(jsonPath("$.membros.size()").value(1));
    }

    @Override
    @SneakyThrows
    public void assertCreatedOrUpdatedEntity(Projeto savedEntity, ProjetoPayloadDTO sentDto) {
        assertThat(savedEntity.getSigla()).isEqualTo(sentDto.getSigla());
        assertThat(savedEntity.getDescricao()).isEqualTo(sentDto.getDescricao());
        assertThat(savedEntity.getDono().getId()).isEqualTo(sentDto.getDono());
        assertThat(savedEntity.getMembros()).hasSize(1).contains(member);
    }

    @Override
    @SneakyThrows
    public void assertResultResponse(ResultActions result, ProjetoPayloadDTO sentDto) {
        result.andExpect(jsonPath("$.sigla").value(sentDto.getSigla()))
                .andExpect(jsonPath("$.descricao").value(sentDto.getDescricao()))
                .andExpect(jsonPath("$.dono").value(sentDto.getDono().toString()))
                .andExpect(jsonPath("$.membros.size()").value(1));
    }

    @Override
    public Projeto persistValidEntity() {
        return repository.save(projetoCanCan(admin, List.of(member)));
    }

    @Override
    public ProjetoPayloadDTO buildValidCreateDto() {
        Projeto projeto = persistValidEntity();
        ProjetoPayloadDTO dto = new ProjetoPayloadDTO(projeto);
        ReflectionUtils.setIn(dto, "id", null);
        ReflectionUtils.setIn(dto, "version", null);
        ReflectionUtils.setIn(dto, "nome", "PROJETO SECUNDÁRIO");
        ReflectionUtils.setIn(dto, "sigla", "ZZZ");
        return dto;
    }

    @Override
    public ProjetoPayloadDTO buildValidUpdateDto(Projeto entity) {
        return new ProjetoPayloadDTO(entity);
    }

    @Override
    public ProjetoFiltroDTO buildValidFilter() {
        return new ProjetoFiltroDTO();
    }

    @Override
    public String getBaseUrl() {
        return "/projeto";
    }

    @Override
    public RequestPostProcessor getJwt() {
        return jwtAdmin(admin.getId());
    }
}