package com.articos.cancan.domain.usuario;

import com.articos.cancan.common.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface UsuarioRepository extends SuperRepository<Usuario> {
    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
