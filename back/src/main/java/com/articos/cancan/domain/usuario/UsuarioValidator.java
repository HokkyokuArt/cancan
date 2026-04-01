package com.articos.cancan.domain.usuario;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.common.exceptions.core.*;
import com.articos.cancan.domain.usuario.dto.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@RequiredArgsConstructor
public class UsuarioValidator extends SuperValidator<Usuario, UsuarioPayloadDTO> {

    private final UsuarioRepository repository;

    @Override
    protected void validateCreate(Usuario entity, UsuarioPayloadDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new DuplicidadeException("Email já cadastrado");
        }
    }

    @Override
    protected void validateUpdate(Usuario entityAntiga, UsuarioPayloadDTO dto) {
        boolean trocouEmail = !entityAntiga.getEmail().equals(dto.getEmail());
        if (trocouEmail && repository.existsByEmail(dto.getEmail())) {
            throw new DuplicidadeException("Email já cadastrado");
        }
    }
}
