package com.articos.cancan.domain.dashboard;

import com.articos.cancan.common.exceptions.core.*;
import com.articos.cancan.domain.dashboard.dto.DashboardRecords.*;
import com.articos.cancan.security.jwt.role.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.annotations.tags.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Operações de dashboard e relatórios resumidos")
public class DashboardRestController {

    private final DashboardService dashboardService;

    @MemberAccess
    @GetMapping("/projeto/{projetoId}/resumo")
    @Operation(
            summary = "Retorna o relatório resumido de um projeto",
            description = """
                    Retorna contadores de tarefas agrupados por status e por prioridade de um projeto.

                    Regras de acesso:
                    - Usuários ADMIN podem consultar qualquer projeto.
                    - Usuários MEMBER só podem consultar projetos dos quais fazem parte.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Relatório resumido retornado com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DashboardResumoProjetoDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "byStatus": {
                                        "TODO": 12,
                                        "IN_PROGRESS": 3,
                                        "DONE": 45
                                      },
                                      "byPriority": {
                                        "LOW": 10,
                                        "MEDIUM": 20,
                                        "HIGH": 25,
                                        "CRITICAL": 5
                                      }
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário não tem permissão para acessar os dados deste projeto",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemaDetalhe.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Projeto não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemaDetalhe.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno inesperado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemaDetalhe.class)
                    )
            )
    })
    public ResponseEntity<DashboardResumoProjetoDTO> resumoProjeto(
            @Parameter(
                    description = "UUID do projeto que será utilizado para gerar o relatório resumido",
                    required = true
            )
            @PathVariable UUID projetoId
    ) {
        return ResponseEntity.ok(dashboardService.resumoProjeto(projetoId));
    }
}