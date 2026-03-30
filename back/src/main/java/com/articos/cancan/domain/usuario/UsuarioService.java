package com.articos.cancan.domain.usuario;

import com.articos.cancan.common.crud.*;
import org.springframework.stereotype.*;

@Service
public class UsuarioService extends SuperService<Usuario> {
    public UsuarioService(UsuarioRepository repository) {
        super(repository);
    }
}
