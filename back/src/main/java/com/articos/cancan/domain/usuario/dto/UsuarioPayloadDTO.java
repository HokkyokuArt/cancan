package com.articos.cancan.domain.usuario.dto;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.usuario.*;
import com.articos.cancan.security.jwt.role.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@NoArgsConstructor
public class UsuarioPayloadDTO extends SuperPayloadDTO<Usuario> {
    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "Email é obrigatório.")
    @Email(message = "Email deve ser válido.")
    @Size(max = 150, message = "Email deve ter no máximo 150 caracteres.")
    private String email;

    @NotBlank(message = "Senha é obrigatória.")
    @Size(min = 8, max = 30, message = "Senha deve ter entre 8 e 30 caracteres.")
    private String senha;

    @NotNull(message = "Role é obrigatória.")
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
