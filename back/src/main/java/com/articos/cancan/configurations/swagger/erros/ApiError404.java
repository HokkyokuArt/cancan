package com.articos.cancan.configurations.swagger.erros;

import com.articos.cancan.common.exceptions.core.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
        responseCode = "404",
        description = "Entidade não encontrada",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProblemaDetalhe.class),
                examples = @ExampleObject(
                        value = """
                                {
                                  "timestamp": "2026-04-01T18:40:21-03:00",
                                  "status": 404,
                                  "code": "NAO_ENCONTRADO",
                                  "error": "EntidadeNaoEncontrada",
                                  "message": "Tarefa não encontrada ao buscar",
                                  "path": "/tarefa/find/56e867e3-ddd0-433d-8dad-eb43a162d127"
                                }
                                """
                )
        )
)
public @interface ApiError404 {
}