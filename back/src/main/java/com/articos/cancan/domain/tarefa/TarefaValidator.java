package com.articos.cancan.domain.tarefa;

import com.articos.cancan.common.*;
import com.articos.cancan.domain.tarefa.dto.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@RequiredArgsConstructor
public class TarefaValidator extends SuperValidator<Tarefa, TarefaPayloadDTO> {

    @Override
    protected void validateCreate(Tarefa entity, TarefaPayloadDTO dto) throws Exception {
        validateResponsavelNaoEstaNoProjeto(entity);
        validateFecharTarefaPrioridadeCritical(entity, dto);
    }

    @Override
    protected void validateUpdate(Tarefa entity, TarefaPayloadDTO dto) throws Exception {
        validateResponsavelNaoEstaNoProjeto(entity);
        validateMoverTarefa(entity, dto);
        validateFecharTarefaPrioridadeCritical(entity, dto);
    }

    private static void validateResponsavelNaoEstaNoProjeto(Tarefa entity) {
//        if (usuarioLogadoEhDonoDoProjeto(entity.getProjeto().getDono().getId()))
//            return;
        boolean responsavelNaoEstaNoProjeto = !entity.getProjeto().getMembros().contains(entity.getResponsavel());
        if (responsavelNaoEstaNoProjeto) {
            throw new RuntimeException();
        }
    }

    private static void validateMoverTarefa(Tarefa entity, TarefaPayloadDTO dto) {
        if (entity.getStatus().isDone() && !dto.getStatus().isInProgress()) {
            throw new RuntimeException();
        }
    }

    private static void validateFecharTarefaPrioridadeCritical(Tarefa entity, TarefaPayloadDTO dto) {
        boolean prioridadeCritical = entity.getPrioridade().isCritical();
        boolean isDono = usuarioLogadoEhDonoDoProjeto(entity.getProjeto().getDono().getId());
        boolean isMovendoPraDone = dto.getStatus().isDone();
        if (!isDono && prioridadeCritical && isMovendoPraDone) {
            throw new RuntimeException();
        }
    }

    private static boolean usuarioLogadoEhDonoDoProjeto(UUID donoProjeto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID usuarioAtual = UUID.fromString(authentication.getName());
        return donoProjeto.equals(usuarioAtual);
    }
}
