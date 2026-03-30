package com.articos.cancan.domain.usuario.dto;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.usuario.*;

public class UsuarioResponseDTO extends SuperPayloadDTO<Usuario> {
    @Override
    public Usuario toEntity() {
        return null;
    }
}
