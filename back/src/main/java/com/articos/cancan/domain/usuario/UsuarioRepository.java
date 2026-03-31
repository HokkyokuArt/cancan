package com.articos.cancan.domain.usuario;

import com.articos.cancan.common.crud.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface UsuarioRepository extends SuperRepository<Usuario> {
    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
            select (u.role = 'A')
            from Usuario u
            where u.id = :usuarioId
            """)
    boolean isAdmin(@Param("usuarioId") UUID usuariosId);
}
