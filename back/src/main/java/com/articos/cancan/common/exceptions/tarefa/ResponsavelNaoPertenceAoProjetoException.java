package com.articos.cancan.common.exceptions.tarefa;

import com.articos.cancan.common.exceptions.core.*;
import org.springframework.http.*;

import static com.articos.cancan.common.exceptions.core.ProblemaCode.*;

public class ResponsavelNaoPertenceAoProjetoException extends BusinessException {

    public ResponsavelNaoPertenceAoProjetoException() {
        super("Responsável atribuido na tarefa não faz parte dos membros do projeto.",
                RESPONSAVEL_NAO_PERTENCE_AO_PROJETO,
                HttpStatus.BAD_REQUEST,
                null);
    }
}
