package com.articos.cancan.domain.usuario;

import com.articos.cancan.common.*;
import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.usuario.dto.*;
import com.articos.cancan.utils.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;

import static com.articos.cancan.domain.usuario.UsuarioDataProvider.novoUsuario;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class UsuarioRestControllerIT extends SuperCrudRestControllerIT<
        Usuario,
        UsuarioPayloadDTO,
        UsuarioResponseDTO,
        UsuarioFiltroDTO,
        AbstractEntityDTO
        > {

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    @SneakyThrows
    public void assertFindResponse(ResultActions result, Usuario entity) {
        result.andExpect(jsonPath("$.nome").value(entity.getNome()))
                .andExpect(jsonPath("$.email").value(entity.getEmail()))
                .andExpect(jsonPath("$.senha").doesNotExist())
                .andExpect(jsonPath("$.role").value(entity.getRole().getDescritivo()));
    }

    @Override
    @SneakyThrows
    public void assertFindToEditResponse(ResultActions result, Usuario entity) {
        result.andExpect(jsonPath("$.nome").value(entity.getNome()))
                .andExpect(jsonPath("$.email").value(entity.getEmail()))
                .andExpect(jsonPath("$.senha").value(nullValue()))
                .andExpect(jsonPath("$.role").value(entity.getRole().name()));
    }

    @Override
    @SneakyThrows
    public void assertCreatedOrUpdatedEntity(Usuario savedEntity, UsuarioPayloadDTO sentDto) {
        assertThat(savedEntity.getNome()).isEqualTo(sentDto.getNome());
        assertThat(savedEntity.getEmail()).isEqualTo(sentDto.getEmail());
        assertThat(savedEntity.getSenha()).isNotEqualTo(passwordEncoder.encode(sentDto.getSenha()));
        assertThat(savedEntity.getRole()).isEqualTo(sentDto.getRole());
    }

    @Override
    @SneakyThrows
    public void assertResultResponse(ResultActions result, UsuarioPayloadDTO sentDto) {
        result.andExpect(jsonPath("$.nome").value(sentDto.getNome()))
                .andExpect(jsonPath("$.email").value(sentDto.getEmail()))
                .andExpect(jsonPath("$.senha").value(nullValue()))
                .andExpect(jsonPath("$.role").value(sentDto.getRole().name()));
    }

    @Override
    public Usuario persistValidEntity() {
        return repository.save(novoUsuario());
    }

    @Override
    public UsuarioPayloadDTO buildValidCreateDto() {
        UsuarioPayloadDTO dto = new UsuarioPayloadDTO(persistValidEntity());
        ReflectionUtils.setIn(dto, "id", null);
        ReflectionUtils.setIn(dto, "version", null);
        ReflectionUtils.setIn(dto, "senha", "13241324");
        ReflectionUtils.setIn(dto, "email", "novo2@email");
        return dto;
    }

    @Override
    public UsuarioPayloadDTO buildValidUpdateDto(Usuario entity) {
        UsuarioPayloadDTO dto = new UsuarioPayloadDTO(entity);
        ReflectionUtils.setIn(dto, "senha", "13241324");
        return dto;
    }

    @Override
    public UsuarioFiltroDTO buildValidFilter() {
        return new UsuarioFiltroDTO();
    }

    @Override
    public String getBaseUrl() {
        return "/usuario";
    }

    @Override
    public RequestPostProcessor getJwt() {
        return jwtAdmin(admin.getId());
    }
}