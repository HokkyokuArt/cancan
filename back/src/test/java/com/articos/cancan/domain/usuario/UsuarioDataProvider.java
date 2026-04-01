package com.articos.cancan.domain.usuario;

import com.articos.cancan.security.jwt.role.*;

public class UsuarioDataProvider {

    public static Usuario usuarioAdmin() {
        Usuario usuario = new Usuario();
        usuario.setNome("NOVO ADMIN");
        usuario.setEmail("novoadmin@admin");
        usuario.setSenha("13241324");
        usuario.setRole(Role.ROLE_ADMIN);
        return usuario;
    }

    public static Usuario usuarioMember() {
        Usuario usuario = new Usuario();
        usuario.setNome("EU MESMO DE NOVO");
        usuario.setEmail("eudenovo@email");
        usuario.setSenha("13241324");
        usuario.setRole(Role.ROLE_MEMBER);
        return usuario;
    }

    public static Usuario novoUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("NOVO USUARIO");
        usuario.setEmail("novo@email");
        usuario.setSenha("13241324");
        usuario.setRole(Role.ROLE_MEMBER);
        return usuario;
    }
}
