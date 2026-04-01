package com.articos.cancan.domain.usuario;

import com.articos.cancan.common.*;
import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.usuario.dto.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioRestController extends SuperRestController<
        Usuario,
        UsuarioResponseDTO,
        UsuarioResponseDTO,
        UsuarioFiltroDTO,
        AbstractEntityDTO
        > {

    public UsuarioRestController(UsuarioService service) {
        super(service, null);
    }
}
