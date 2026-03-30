package com.articos.cancan.domain.tarefa;

import com.articos.cancan.common.*;
import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.projeto.*;
import com.articos.cancan.domain.tarefa.dto.*;
import com.articos.cancan.domain.usuario.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefa")
public class TarefaRestController extends SuperRestController<
        Tarefa,
        TarefaPayloadDTO,
        TarefaResponseDTO,
        TarefaFiltroDTO,
        AbstractEntityDTO
        > {

    private final UsuarioService usuarioService;
    private final ProjetoService projetoService;

    private TarefaService service() {
        return (TarefaService) service;
    }

    public TarefaRestController(TarefaService service, TarefaValidator validator, UsuarioService usuarioService, ProjetoService projetoService) {
        super(service, validator);
        this.usuarioService = usuarioService;
        this.projetoService = projetoService;
    }

    @Override
    protected void updateValues(Tarefa tarefa, TarefaPayloadDTO dto) {
        Usuario responsavel = usuarioService.load(dto.getResponsavel());
        Projeto projeto = projetoService.load(dto.getProjeto());
        Integer proximoNumero = service().proximoNumero(projeto.getId());
        tarefa.setValues(dto, responsavel, projeto, proximoNumero);
    }
}
