package com.articos.cancan.domain.tarefa;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.dashboard.*;
import com.articos.cancan.domain.tarefa.statustarefa.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface TarefaRepository extends SuperRepository<Tarefa> {
    @Query("""
                select coalesce(max(t.sequencial), 0) + 1
                from Tarefa t
                where t.projeto.id = :projetoId
            """)
    Integer findProximoCodigoByProjeto(@Param("projetoId") UUID projetoId);

    long countByResponsavelIdAndStatus(UUID responsavelId, StatusTarefa status);

    @Query("""
                select cast(t.status as string) as key, count(t) as total
                from Tarefa t
                where t.projeto.id = :projetoId
                group by t.status
            """)
    List<DashboardCountProjection> countByStatus(@Param("projetoId") UUID projetoId);

    @Query("""
                select cast(t.prioridade as string) as key, count(t) as total
                from Tarefa t
                where t.projeto.id = :projetoId
                group by t.prioridade
            """)
    List<DashboardCountProjection> countByPrioridade(@Param("projetoId") UUID projetoId);
}
