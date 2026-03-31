package com.articos.cancan.common.exceptions.core;

import lombok.*;
import org.springframework.http.*;

import java.util.*;

@Getter
public abstract class BusinessException extends RuntimeException {

    private final ProblemaCode code;
    private final HttpStatus status;
    private final Map<String, Object> extra;

    protected BusinessException(String message, ProblemaCode code, HttpStatus status, Map<String, Object> extra) {
        super(message);
        this.code = code;
        this.status = status;
        this.extra = extra;
    }
}