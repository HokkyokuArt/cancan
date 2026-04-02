package com.articos.cancan.domain.usuario.dto;

import com.articos.cancan.common.*;
import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.usuario.*;
import com.articos.cancan.security.jwt.role.*;
import lombok.*;

@Getter

public class UsuarioListResponseDTO extends AbstractEntityDTO {

    private String nome;
    private String email;
    private String role;

    public UsuarioListResponseDTO(Usuario entity) {
        super(entity);
        this.nome = entity.getNome();
        this.email = entity.getEmail();
        this.role = entity.getRole().getDescritivo();
    }
}
