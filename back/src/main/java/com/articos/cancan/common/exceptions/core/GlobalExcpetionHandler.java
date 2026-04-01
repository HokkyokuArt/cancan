package com.articos.cancan.common.exceptions.core;

import jakarta.servlet.http.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authorization.*;
import org.springframework.web.bind.*;
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

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ProblemaDetalhe> handleInternalAuthenticationService(
            InternalAuthenticationServiceException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ProblemaDetalhe body = ProblemaDetalhe.builder()
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(ex.getClass().getSimpleName())
                .code(ProblemaCode.TOKEN_INVALIDO)
                .message("Email ou senha incorreto.")
                .path(request.getRequestURI())
                .extra(Map.of("err", ex.getMessage()))
                .build();
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemaDetalhe> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Map<String, Object> extra = new HashMap<>();
        Map<String, String> campos = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                campos.put(error.getField(), error.getDefaultMessage())
        );
        extra.put("campos", campos);
        ProblemaDetalhe body = ProblemaDetalhe.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error(ex.getClass().getSimpleName())
                .code(ProblemaCode.DADOS_INVALIDOS)
                .message("Dados inválidos.")
                .path(request.getRequestURI())
                .extra(extra)
                .build();

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ProblemaDetalhe> handleAuthorizationDenied(
            AuthorizationDeniedException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ProblemaDetalhe body = ProblemaDetalhe.builder()
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(ex.getClass().getSimpleName())
                .code(ProblemaCode.TOKEN_INVALIDO)
                .message("Acesso negado!")
                .path(request.getRequestURI())
                .extra(Map.of("err", ex.getMessage()))
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

//        ex.printStackTrace();
        return ResponseEntity.status(status).body(body);
    }
}
