package com.articos.cancan.utils;

import com.articos.cancan.configurations.swagger.erros.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("utils")
@Tag(name = "Utils", description = "Rest com métodos utils para o front")
public class UtilsRestController {

    @Operation(
            summary = "Buscar valores de um enum",
            description = "Retorna os valores de um enum informado pelo nome, incluindo seus dados completos para uso no front."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Enum encontrado com sucesso",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = Object.class,
                                            description = "Lista de objetos representando os valores do enum"
                                    )
                            ),
                            examples = @ExampleObject(
                                    name = "Exemplo de retorno",
                                    value = """
                                            [
                                              {
                                                "name": "ADMIN",
                                                "codigo": "A",
                                                "descritivo": "ADMIN"
                                              },
                                              {
                                                "name": "MEMBER",
                                                "codigo": "M",
                                                "descritivo": "MEMBER"
                                              }
                                            ]
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Nome do enum inválido ou não informado"
            ),
    })
    @ApiError404
    @GetMapping("/enum/{enumName}")
    public ResponseEntity<List<Map<String, Object>>> getEnum(
            @Parameter(
                    description = "Nome do enum que deve ser buscado",
                    example = "Role"
            )
            @PathVariable @NotEmpty String enumName
    ) {
        Class<? extends Enum<?>> enumClass = ReflectionUtils.findEnumClass(enumName);
        return ResponseEntity.ok(ReflectionUtils.getEnumFull(enumClass));
    }
}