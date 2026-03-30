package com.articos.cancan.domain.projeto;

import com.articos.cancan.common.*;
import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.projeto.dto.*;
import com.articos.cancan.domain.usuario.*;
import com.articos.cancan.security.jwt.role.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/projeto")
public class ProjetoRestController extends SuperRestController<
        Projeto,
        ProjetoPayloadDTO,
        ProjetoPayloadDTO,
        ProjetoFiltroDTO,
        AbstractEntityDTO
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
    @PostMapping("pageable")
    public ResponseEntity<Page<AbstractEntityDTO>> pageable(ProjetoFiltroDTO filtro, Pageable pageable) {
        return super.pageable(filtro, pageable);
    }

    @AdminOnly
    @Override
    @GetMapping("find/{id}")
    public ResponseEntity<ProjetoPayloadDTO> find(UUID id) {
        return super.find(id);
    }

    @AdminOnly
    @Override
    @PostMapping("create")
    public ResponseEntity<ProjetoPayloadDTO> create(ProjetoPayloadDTO dto) throws Exception {
        return super.create(dto);
    }

    @AdminOnly
    @Override
    @PutMapping("update")
    public ResponseEntity<ProjetoPayloadDTO> update(ProjetoPayloadDTO dto) throws Exception {
        return super.update(dto);
    }

    @AdminOnly
    @Override
    @DeleteMapping("delete/{id}")
    public ResponseEntity<ProjetoPayloadDTO> delete(UUID id) throws Exception {
        return super.delete(id);
    }

    @Override
    protected void updateValues(Projeto entity, ProjetoPayloadDTO dto) {
        Usuario dono = usuarioService.loadWithExcption(dto.getDono());
        Collection<Usuario> values = usuarioService.findAll(dto.getMembros()).values();
        Set<Usuario> membros = new HashSet<>(values);
        entity.setValues(dto, dono, membros);
    }
}
