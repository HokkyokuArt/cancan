package com.articos.cancan.common.exceptions.tarefa;

import com.articos.cancan.common.exceptions.core.*;
import org.springframework.http.*;

import static com.articos.cancan.common.exceptions.core.ProblemaCode.*;

public class FecharTarefaException extends BusinessException {
    public FecharTarefaException() {
        super("Apenas usuários ADMIN podem fechar tarefas CRITICAL.",
                FECHAR_TAREFA,
                HttpStatus.BAD_REQUEST,
                null);
    }
}
