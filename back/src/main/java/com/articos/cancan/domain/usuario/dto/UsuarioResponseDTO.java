package com.articos.cancan.domain.usuario.dto;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.usuario.*;
import lombok.*;

@NoArgsConstructor
@Getter
public class UsuarioResponseDTO extends SuperResponseDTO<Usuario> {
    private String nome;
    private String email;
    private String role;

    public UsuarioResponseDTO(Usuario entity) {
        super(entity);
        this.nome = entity.getNome();
        this.email = entity.getEmail();
        this.role = entity.getRole().getDescritivo();
    }
}
