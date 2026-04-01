package com.articos.cancan.domain.projeto;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.projeto.dto.*;
import com.articos.cancan.domain.usuario.*;
import com.articos.cancan.security.jwt.role.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/projeto")
@Tag(name = "Projeto", description = "Operações relacionadas a projeto")
public class ProjetoRestController extends SuperRestController<
        Projeto,
        ProjetoPayloadDTO,
        ProjetoPayloadDTO,
        ProjetoFiltroDTO,
        ProjetoListResponsavelDTO
        > {

    private final UsuarioService usuarioService;

    public ProjetoRestController(
            ProjetoService service,
            ProjetoValidator validator,
            UsuarioService usuarioService
    ) {
        super(service, validator);
        this.usuarioService = usuarioService;
    }

    @AdminOnly
    @Override
    @PostMapping("create")
    public ResponseEntity<ProjetoPayloadDTO> create(ProjetoPayloadDTO dto) {
        return super.create(dto);
    }

    @AdminOnly
    @Override
    @PutMapping("update")
    public ResponseEntity<ProjetoPayloadDTO> update(ProjetoPayloadDTO dto) {
        return super.update(dto);
    }

    @AdminOnly
    @Override
    @DeleteMapping("delete/{id}")
    public ResponseEntity<ProjetoPayloadDTO> delete(UUID id) {
        return super.delete(id);
    }

    @Override
    protected void updateValues(Projeto entity, ProjetoPayloadDTO dto) {
        Usuario dono = usuarioService.loadWithException(dto.getDono(), "buscar dono");
        Collection<Usuario> values = usuarioService.findAll(dto.getMembros()).values();
        Set<Usuario> membros = new HashSet<>(values);
        entity.setValues(dto, dono, membros);
    }
}
