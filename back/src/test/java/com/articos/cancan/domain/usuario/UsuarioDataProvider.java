package com.articos.cancan.domain.usuario;

import com.articos.cancan.security.jwt.role.*;
import com.articos.cancan.utils.*;

import java.util.*;

public class UsuarioDataProvider {

    public static Usuario admin(UUID id, int version) {
        Usuario usuario = new Usuario();
        usuario.setNome("NOVO ADMIN");
        usuario.setEmail("novoadmin@admin");
        usuario.setSenha("13241324");
        usuario.setRole(Role.ROLE_ADMIN);
        ReflectionUtils.setIn(usuario, "id", id);
        ReflectionUtils.setIn(usuario, "version", version);
        return usuario;
    }

    public static Usuario admin() {
        return admin(null, 0);
    }

    public static Usuario member() {
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
