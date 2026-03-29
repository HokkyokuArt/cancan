package com.articos.cancan.domain.usuario;

import com.articos.cancan.common.*;
import com.articos.cancan.security.jwt.role.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USUARIO")
@Getter
@Setter
public class Usuario extends SuperEntity {

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 255)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.ROLE_MEMBER;

    public Usuario() {
        super();
    }

    public Usuario(SuperPaylodResponeDTO dto) {
        super(dto);
    }

    @Override
    public String getDescritivo() {
        return this.nome;
    }

    @Override
    public Object toDTO() {
        return null;
    }
}
