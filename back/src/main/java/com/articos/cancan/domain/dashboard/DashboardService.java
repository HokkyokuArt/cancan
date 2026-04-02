package com.articos.cancan.domain.dashboard;

import com.articos.cancan.common.exceptions.core.*;
import com.articos.cancan.common.interfaces.*;
import com.articos.cancan.domain.auth.*;
import com.articos.cancan.domain.dashboard.*;
import com.articos.cancan.domain.dashboard.dto.DashboardRecords.*;
import com.articos.cancan.domain.projeto.*;
import com.articos.cancan.domain.tarefa.*;
import com.articos.cancan.domain.tarefa.prioridadetarefa.*;
import com.articos.cancan.domain.tarefa.statustarefa.*;
import com.articos.cancan.domain.usuario.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final TarefaRepository tarefaRepository;
    private final ProjetoRepository projetoRepository;
    private final UsuarioRepository usuarioRepository;

    public DashboardResumoProjetoDTO resumoProjeto(UUID projetoId) {
        UUID usuarioAtual = AuthContext.getUsuarioAtual();

        validarAcessoProjeto(projetoId, usuarioAtual);

        Map<StatusTarefa, Long> byStatus = tarefaRepository.countByStatus(projetoId)
                .stream()
                .collect(Collectors.toMap(
                        s -> EnumAttributeConverterDescritivo.ofCodigo(StatusTarefa.class, s.getKey()),
                        DashboardCountProjection::getTotal
                ));

        Map<PrioridadeTarefa, Long> byPriority = tarefaRepository.countByPrioridade(projetoId)
                .stream()
                .collect(Collectors.toMap(
                        s -> EnumAttributeConverterDescritivo.ofCodigo(PrioridadeTarefa.class, s.getKey()),
                        DashboardCountProjection::getTotal
                ));

        return new DashboardResumoProjetoDTO(byStatus, byPriority);
    }

    private void validarAcessoProjeto(UUID projetoId, UUID usuarioAtual) {
        boolean isAdmin = usuarioRepository.isAdmin(usuarioAtual);

        if (isAdmin) {
            boolean projetoExiste = projetoRepository.existsById(projetoId);
            if (!projetoExiste) {
                throw new EntidadeNaoEncontradaException(Projeto.class, "validar acesso");
            }
            return;
        }

        boolean temAcesso = projetoRepository.usuarioParticipaDoProjeto(projetoId, usuarioAtual);
        if (!temAcesso) {
            throw new UsuarioSemAcessoException();
        }
    }
}
