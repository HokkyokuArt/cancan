package com.articos.cancan.common.exceptions.tarefa;

import com.articos.cancan.common.exceptions.core.*;
import com.articos.cancan.domain.tarefa.statustarefa.*;
import org.springframework.http.*;

public class MoverTarefaException extends BusinessException {

    public MoverTarefaException(StatusTarefa novoStatus) {
        super(String.format("Não foi possível mover a tarefa para o status %s.", novoStatus.getDescritivo()),
                ProblemaCode.MOVER_TAREFA,
                HttpStatus.BAD_REQUEST,
                null);
    }
}
