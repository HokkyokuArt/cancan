package com.articos.cancan.domain.usuario;

import com.articos.cancan.common.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class UsuarioService extends SuperService<Usuario> {
    public UsuarioService(JpaRepository<Usuario, UUID> repository) {
        super(repository);
    }
}
