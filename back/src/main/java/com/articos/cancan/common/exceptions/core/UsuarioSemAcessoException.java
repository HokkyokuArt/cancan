package com.articos.cancan.common.exceptions.core;

import org.springframework.http.*;

public class UsuarioSemAcessoException extends BusinessException {
    public UsuarioSemAcessoException() {
        super(
                "Você não tem permissão para visualizar.",
                ProblemaCode.USUARIO_SEM_ACESSO,
                HttpStatus.FORBIDDEN,
                null
        );
    }
}
