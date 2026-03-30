package com.articos.cancan.domain.usuario;

import com.articos.cancan.common.*;
import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.usuario.dto.*;
import com.articos.cancan.security.jwt.role.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USUARIO")
@Getter
@Setter
public class Usuario extends SuperEntity<UsuarioResponseDTO, UsuarioResponseDTO, AbstractEntityDTO> {

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 255)
    private String senha;

    @Convert(converter = RoleAttributeConverter.class)
    @Column(nullable = false)
    private Role role = Role.ROLE_MEMBER;

    public Usuario() {
        super();
    }

    public Usuario(SuperPayloadDTO dto) {
        super(dto);
    }

    @Override
    public String getDescritivo() {
        return this.nome;
    }

    @Override
    public UsuarioResponseDTO toDTO() {
        return null;
    }

    @Override
    public AbstractEntityDTO toListDTO() {
        return null;
    }

    @Override
    public void setValues(UsuarioResponseDTO usuarioResponseDTO) {

    }
}
