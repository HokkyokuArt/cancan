package com.articos.cancan.common.exceptions.core;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Schema(name = "ProblemaDetalhe", description = "Estrutura padrão de erro da API")
public class ProblemaDetalhe {

    @Schema(description = "Data e hora em que o erro ocorreu", example = "2026-04-01T18:40:21-03:00")
    private OffsetDateTime timestamp;

    @Schema(description = "Código HTTP do erro", example = "404")
    private Integer status;

    @Schema(description = "Código interno do problema")
    private ProblemaCode code;

    @Schema(description = "Nome do erro HTTP", example = "Not Found")
    private String error;

    @Schema(description = "Mensagem detalhada do erro", example = "Não foi possível buscar o registro informado.")
    private String message;

    @Schema(description = "Caminho da requisição que gerou o erro", example = "/tarefa/find/550e8400-e29b-41d4-a716-446655440000")
    private String path;

    @Schema(description = "Informações extras do erro, quando existirem")
    private Map<String, Object> extra;
}
