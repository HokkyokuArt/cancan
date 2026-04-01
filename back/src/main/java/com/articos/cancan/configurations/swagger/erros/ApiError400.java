package com.articos.cancan.configurations.swagger.erros;

import com.articos.cancan.common.exceptions.core.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
        responseCode = "400",
        description = "Erro na request",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProblemaDetalhe.class),
                examples = @ExampleObject(
                        value = """
                                {
                                  "timestamp": "2026-04-01T18:40:21-03:00",
                                  "status": 400,
                                  "code": "DADOS_INVALIDOS",
                                  "error": "Erro qualquer",
                                  "message": "Dados inválidos",
                                  "path": "/tarefa/update"
                                }
                                """
                )
        )
)
public @interface ApiError400 {
}