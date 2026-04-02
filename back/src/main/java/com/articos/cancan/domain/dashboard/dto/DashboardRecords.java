package com.articos.cancan.domain.dashboard.dto;

import com.articos.cancan.domain.tarefa.prioridadetarefa.*;
import com.articos.cancan.domain.tarefa.statustarefa.*;
import io.swagger.v3.oas.annotations.media.*;

import java.util.*;

public class DashboardRecords {


    @Schema(description = "Resumo do projeto com contadores de tarefas por status e prioridade")
    public record DashboardResumoProjetoDTO(
            @Schema(description = "Quantidade de tarefas agrupadas por status")
            Map<StatusTarefa, Long> byStatus,
            @Schema(
                    description = "Quantidade de tarefas agrupadas por status",
                    example = "{\"TODO\":12,\"IN_PROGRESS\":3,\"DONE\":45}"
            )
            Map<PrioridadeTarefa, Long> byPriority
    ) {
    }
}
