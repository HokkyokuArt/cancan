package com.articos.cancan.common.exceptions.tarefa;

import com.articos.cancan.common.exceptions.core.*;
import com.articos.cancan.domain.tarefa.statustarefa.*;
import com.articos.cancan.domain.usuario.*;
import org.springframework.http.*;

import static com.articos.cancan.common.exceptions.core.ProblemaCode.*;

public class AtingiuLimiteTarefasException extends BusinessException {
    public AtingiuLimiteTarefasException(
            Usuario usuario,
            StatusTarefa statusTarefa,
            long limite
    ) {
        super(String.format("%s atingiu o limite máximo de tarefas (%s) com status %s.",
                        usuario.getNome(),
                        limite,
                        statusTarefa.getDescritivo()
                ),
                ATINGIU_LIMITE_TAREFAS,
                HttpStatus.CONFLICT,
                null);
    }
}
