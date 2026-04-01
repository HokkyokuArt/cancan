package com.articos.cancan.domain.tarefa;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.auth.*;
import com.articos.cancan.domain.projeto.*;
import com.articos.cancan.domain.tarefa.dto.*;
import com.articos.cancan.domain.usuario.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/tarefa")
@Tag(name = "Tarefa", description = "Operações relacionadas a tarefa")
public class TarefaRestController extends SuperRestController<
        Tarefa,
        TarefaPayloadDTO,
        TarefaResponseDTO,
        TarefaFiltroDTO,
        TarefaListResponseDTO
        > {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final ProjetoService projetoService;

    private TarefaService service() {
        return (TarefaService) service;
    }

    public TarefaRestController(
            TarefaService service,
            TarefaValidator validator,
            UsuarioService usuarioService,
            UsuarioRepository usuarioRepository,
            ProjetoService projetoService) {
        super(service, validator);
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.projetoService = projetoService;
    }

    @Override
    protected void updateValues(Tarefa tarefa, TarefaPayloadDTO dto) {
        Usuario responsavel = usuarioService.load(dto.getResponsavel());
        Projeto projeto = projetoService.load(dto.getProjeto());
        Integer proximoNumero = service().proximoNumero(projeto.getId());
        tarefa.setValues(dto, responsavel, projeto, proximoNumero);
    }

    @Override
    protected TarefaFiltroDTO enriquecerFiltro(TarefaFiltroDTO filtro) {
        UUID usuarioAtual = AuthContext.getUsuarioAtual();
        boolean isAdmin = usuarioRepository.isAdmin(usuarioAtual);
        filtro.setIsAdmin(isAdmin);
        filtro.setUsuarioAtual(usuarioAtual);
        return filtro;
    }
}
