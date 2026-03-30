package com.articos.cancan.domain.tarefa;

import com.articos.cancan.common.*;
import com.articos.cancan.domain.tarefa.dto.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefa")
public class TarefaRestController extends SuperRestController<
        Tarefa,
        TarefaPayloadDTO,
        TarefaPayloadDTO,
        TarefaFiltroDTO,
        AbstractEntityDTO
        > {

    public TarefaRestController(TarefaService service, TarefaValidator validator) {
        super(service, validator);
    }
}
