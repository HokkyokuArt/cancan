package com.articos.cancan.domain.projeto;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.common.exceptions.projeto.*;
import com.articos.cancan.domain.projeto.dto.*;
import com.articos.cancan.domain.usuario.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ProjetoValidator extends SuperValidator<Projeto, ProjetoPayloadDTO> {

    private final UsuarioRepository usuarioRepository;

    @Override
    protected void validateCreate(Projeto entity, ProjetoPayloadDTO dto) {
        validateDonoIsAdmin(dto.getDono());
    }

    @Override
    protected void validateUpdate(Projeto entityAntiga, ProjetoPayloadDTO dto) {
        validateDonoIsAdmin(dto.getDono());
    }

    private void validateDonoIsAdmin(UUID dono) {
        if (!usuarioRepository.isAdmin(dono)) {
            throw new DonoNaoPossuiRoleAdminException();
        }
    }
}
