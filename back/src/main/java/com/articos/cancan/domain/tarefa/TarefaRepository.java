package com.articos.cancan.domain.tarefa;

import com.articos.cancan.common.crud.*;
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
}
