package com.articos.cancan.domain.usuario;

import com.articos.cancan.common.*;
import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.usuario.dto.*;
import com.articos.cancan.security.jwt.role.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuário", description = "Operações relacionadas a usuário")
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

    @Hidden
    @Override
    @MemberAccess
    @PostMapping("create")
    public ResponseEntity<UsuarioResponseDTO> create(UsuarioResponseDTO dto) {
        throw new ResponseStatusException(
                HttpStatus.METHOD_NOT_ALLOWED,
                "Não é permitido criar usuários por este endpoint."
        );
    }

    @Hidden
    @Override
    @MemberAccess
    @PostMapping("update")
    public ResponseEntity<UsuarioResponseDTO> update(UsuarioResponseDTO dto) {
        throw new ResponseStatusException(
                HttpStatus.METHOD_NOT_ALLOWED,
                "Não é permitido atualizar usuários por este endpoint."
        );
    }
}
