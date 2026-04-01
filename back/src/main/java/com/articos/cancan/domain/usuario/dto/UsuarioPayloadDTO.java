package com.articos.cancan.domain.usuario.dto;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.usuario.*;
import com.articos.cancan.security.jwt.role.*;
import lombok.*;

@Getter
@NoArgsConstructor
public class UsuarioPayloadDTO extends SuperPayloadDTO<Usuario> {
    private String nome;
    private String email;
    private String senha;
    private Role role;

    public UsuarioPayloadDTO(Usuario entity) {
        super(entity);
        this.nome = entity.getNome();
        this.email = entity.getEmail();
        this.role = entity.getRole();
    }

    @Override
    public Usuario toEntity() {
        return new Usuario(this);
    }
}
