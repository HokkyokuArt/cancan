package com.articos.cancan.domain.tarefa;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.common.exceptions.core.*;
import com.articos.cancan.common.exceptions.tarefa.*;
import com.articos.cancan.domain.tarefa.dto.*;
import com.articos.cancan.domain.tarefa.statustarefa.*;
import com.articos.cancan.domain.usuario.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

import static com.articos.cancan.domain.auth.AuthContext.*;

@Component
@RequiredArgsConstructor
public class TarefaValidator extends SuperValidator<Tarefa, TarefaPayloadDTO> {

    private final TarefaRepository repository;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void validateCreate(Tarefa entity, TarefaPayloadDTO dto) {
        validateResponsavelNaoEstaNoProjeto(entity);
    }

    @Override
    protected void validateUpdate(Tarefa entityAntiga, TarefaPayloadDTO dto) {
        validateResponsavelNaoEstaNoProjeto(entityAntiga);
        validateMoverTarefa(entityAntiga, dto);
        validateFecharTarefaPrioridadeCritical(entityAntiga, dto);
        validateAtingiuMaximoTarefasInProgress(entityAntiga, dto);
    }

    @Override
    protected void validateView(Tarefa entity) {
        UUID usuarioAtualId = getUsuarioAtual();
        if (usuarioRepository.isAdmin(usuarioAtualId))
            return;

        boolean pertenceAoProjeto = entity.getProjeto().getMembros().stream().anyMatch(s -> s.getId().equals(usuarioAtualId));
        if (!pertenceAoProjeto) {
            throw new UsuarioSemAcessoException();
        }
    }

    protected static void validateResponsavelNaoEstaNoProjeto(Tarefa entity) {
        if (usuarioLogadoEhDonoDoProjeto(entity.getProjeto().getDono().getId()))
            return;
        boolean responsavelNaoEstaNoProjeto = !entity.getProjeto().getMembros().contains(entity.getResponsavel());
        if (responsavelNaoEstaNoProjeto) {
            throw new ResponsavelNaoPertenceAoProjetoException();
        }
    }

    protected static void validateMoverTarefa(Tarefa entity, TarefaPayloadDTO dto) {
        if (entity.getStatus().isDone() && !dto.getStatus().isInProgress()) {
            throw new MoverTarefaException(dto.getStatus());
        }
    }

    protected void validateFecharTarefaPrioridadeCritical(Tarefa entity, TarefaPayloadDTO dto) {
        boolean prioridadeCritical = entity.getPrioridade().isCritical();
        boolean isMovendoPraDone = dto.getStatus().isDone();
        if (!usuarioRepository.isAdmin(getUsuarioAtual()) && prioridadeCritical && isMovendoPraDone) {
            throw new FecharTarefaException();
        }
    }

    protected void validateAtingiuMaximoTarefasInProgress(Tarefa entity, TarefaPayloadDTO dto) {
        Usuario responsavel = entity.getResponsavel();
        StatusTarefa status = dto.getStatus();
        if (status.isInProgress()) {
            long quantidade = repository.countByResponsavelIdAndStatus(responsavel.getId(), status);
            long limite = 5;
            if (quantidade >= limite) {
                throw new AtingiuLimiteTarefasException(responsavel, status, limite);
            }
        }
    }

    protected static boolean usuarioLogadoEhDonoDoProjeto(UUID donoProjeto) {
        return donoProjeto.equals(getUsuarioAtual());
    }
}
