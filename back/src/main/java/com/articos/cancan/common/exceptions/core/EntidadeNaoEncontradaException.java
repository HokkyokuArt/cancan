package com.articos.cancan.common.exceptions.core;

import org.springframework.http.*;

import static com.articos.cancan.common.exceptions.core.ProblemaCode.*;
import static com.articos.cancan.utils.EntidadeFrontFriendlyUtils.*;

public class EntidadeNaoEncontradaException extends BusinessException {

    public EntidadeNaoEncontradaException(Class<?> entityClass, String acao) {
        super(getCustomMsg(entityClass, acao), NAO_ENCONTRADO, HttpStatus.NOT_FOUND, null);
    }

    private static String getCustomMsg(Class<?> entityClass, String acao) {
        return getArtigo(entityClass)
                .apply(getNome(entityClass) + " não encontrad%s ao " + acao + ".");

    }
}
