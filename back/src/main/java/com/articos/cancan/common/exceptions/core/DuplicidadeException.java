package com.articos.cancan.common.exceptions.core;

import org.springframework.http.*;

import static com.articos.cancan.common.exceptions.core.ProblemaCode.*;

public class DuplicidadeException extends BusinessException {

    public DuplicidadeException(String message) {
        super(message, DUPLICIDADE, HttpStatus.CONFLICT, null);
    }
}
