package com.articos.cancan.common.exceptions.tarefa;

import com.articos.cancan.common.exceptions.core.*;
import org.springframework.http.*;

public class UsuarioSemAcessoATarefaException extends BusinessException {
    public UsuarioSemAcessoATarefaException() {
        super(
                "Você não tem permissão para visualizar esta tarefa.",
                ProblemaCode.USUARIO_SEM_ACESSO_TAREFA,
                HttpStatus.FORBIDDEN,
                null
        );
    }
}
