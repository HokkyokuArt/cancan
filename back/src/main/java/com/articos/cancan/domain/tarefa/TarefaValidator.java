package com.articos.cancan.domain.tarefa;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.tarefa.dto.*;
import com.articos.cancan.domain.tarefa.statustarefa.*;
import com.articos.cancan.domain.usuario.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@RequiredArgsConstructor
public class TarefaValidator extends SuperValidator<Tarefa, TarefaPayloadDTO> {

    private final TarefaRepository repository;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void validateCreate(Tarefa entity, TarefaPayloadDTO dto) throws Exception {
        validateResponsavelNaoEstaNoProjeto(entity);
    }

    @Override
    protected void validateUpdate(Tarefa entity, TarefaPayloadDTO dto) throws Exception {
        validateResponsavelNaoEstaNoProjeto(entity);
        validateMoverTarefa(entity, dto);
        validateFecharTarefaPrioridadeCritical(entity, dto);
        validateAtingiuMaximoTarefasInProgress(entity, dto);
    }

    private static void validateResponsavelNaoEstaNoProjeto(Tarefa entity) {
        if (usuarioLogadoEhDonoDoProjeto(entity.getProjeto().getDono().getId()))
            return;
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

    private void validateFecharTarefaPrioridadeCritical(Tarefa entity, TarefaPayloadDTO dto) {
        boolean prioridadeCritical = entity.getPrioridade().isCritical();
        Usuario usuario = usuarioRepository.findById(usuarioLogado()).orElseThrow(RuntimeException::new);
        boolean isMovendoPraDone = dto.getStatus().isDone();
        if (usuario.getRole().isMember() && prioridadeCritical && isMovendoPraDone) {
            throw new RuntimeException();
        }
    }

    private void validateAtingiuMaximoTarefasInProgress(Tarefa entity, TarefaPayloadDTO dto) {
        Usuario responsavel = entity.getResponsavel();
        StatusTarefa status = dto.getStatus();
        if (status.isInProgress()) {
            long quantidade = repository.countByResponsavelIdAndStatus(responsavel.getId(), status);
            if (quantidade >= 5) {
                throw new RuntimeException();
            }
        }
    }

    private static boolean usuarioLogadoEhDonoDoProjeto(UUID donoProjeto) {
        return donoProjeto.equals(usuarioLogado());
    }

    private static UUID usuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return UUID.fromString(authentication.getName());
    }
}
