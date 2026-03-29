package com.articos.cancan.domain.projeto;

import com.articos.cancan.common.*;
import com.articos.cancan.domain.projeto.dto.*;
import com.articos.cancan.domain.usuario.*;
import com.articos.cancan.security.jwt.role.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/projeto")
public class ProjetoRestController extends SuperRestController<Projeto, ProjetoPayloadDTO, ProjetoPayloadDTO> {

    private final UsuarioService usuarioService;

    public ProjetoRestController(ProjetoService service, UsuarioService usuarioService) {
        super(service);
        this.usuarioService = usuarioService;
    }

    @AdminOnly
    @Override
    @GetMapping("find/{id}")
    public ResponseEntity<ProjetoPayloadDTO> find(UUID id) {
        return super.find(id);
    }

    @Override
    public Projeto convertDTOToEntity(ProjetoPayloadDTO dto) {
        Usuario dono = usuarioService.loadWithExcption(dto.getDono());
        Collection<Usuario> values = usuarioService.findAll(dto.getMembros()).values();
        Set<Usuario> membros = new HashSet<>(values);
        return new Projeto(dto, dono, membros);
    }
}
