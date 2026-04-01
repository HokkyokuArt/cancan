package com.articos.cancan.common.exceptions.core;

import jakarta.servlet.http.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.*;

@RestControllerAdvice
public class GlobalExcpetionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ProblemaDetalhe> handleBusinessException(
            BusinessException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = ex.getStatus();
        ProblemaDetalhe body = ProblemaDetalhe.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error(ex.getClass().getSimpleName())
                .code(ex.getCode())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemaDetalhe> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemaDetalhe body = ProblemaDetalhe.builder()
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(ex.getClass().getSimpleName())
                .code(ProblemaCode.ERRO_INTERNO)
                .message("Ocorreu um erro interno inesperado.")
                .path(request.getRequestURI())
                .extra(Map.of("err", ex.getMessage()))
                .build();

        ex.printStackTrace();
        return ResponseEntity.status(status).body(body);
    }
}
