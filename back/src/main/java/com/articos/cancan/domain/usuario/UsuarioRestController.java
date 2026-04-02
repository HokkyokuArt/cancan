package com.articos.cancan.domain.usuario;

import com.articos.cancan.common.*;
import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.usuario.dto.*;
import com.articos.cancan.security.jwt.role.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.crypto.password.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuário", description = "Operações relacionadas a usuário")
public class UsuarioRestController extends SuperRestController<
        Usuario,
        UsuarioPayloadDTO,
        UsuarioResponseDTO,
        UsuarioFiltroDTO,
        UsuarioListResponseDTO
        > {

    private final PasswordEncoder passwordEncoder;

    public UsuarioRestController(
            UsuarioService service,
            UsuarioValidator validator,
            PasswordEncoder passwordEncoder) {
        super(service, validator);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @AdminOnly
    @PostMapping("autocomplete")
    public ResponseEntity<Page<AbstractEntityDTO>> autocomplete(UsuarioFiltroDTO filtro, Pageable pageable) {
        return super.autocomplete(filtro, pageable);
    }

    @Override
    @AdminOnly
    @PostMapping("pageable")
    public ResponseEntity<Page<UsuarioListResponseDTO>> pageable(UsuarioFiltroDTO filtro, Pageable pageable) {
        return super.pageable(filtro, pageable);
    }

    @Override
    @AdminOnly
    @GetMapping("find/{id}")
    public ResponseEntity<UsuarioResponseDTO> find(UUID id) {
        return super.find(id);
    }

    @Override
    @AdminOnly
    @GetMapping("find-to-edit/{id}")
    public ResponseEntity<UsuarioPayloadDTO> findToEdit(UUID id) {
        return super.findToEdit(id);
    }

    @Override
    @AdminOnly
    @PostMapping("create")
    public ResponseEntity<UsuarioPayloadDTO> create(UsuarioPayloadDTO dto) {
        return super.create(dto);
    }

    @Hidden
    @Override
    @AdminOnly
    @PutMapping("update")
    public ResponseEntity<UsuarioPayloadDTO> update(UsuarioPayloadDTO dto) {
        return super.update(dto);
    }

    @Override
    @AdminOnly
    @DeleteMapping("delete/{id}")
    public ResponseEntity<UsuarioPayloadDTO> delete(UUID id) {
        return super.delete(id);
    }

    @Override
    protected void updateValues(Usuario usuario, UsuarioPayloadDTO dto) {
        usuario.setNome(dto.getNome().toUpperCase());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
    }
}
