package com.articos.cancan.domain.projeto;

import com.articos.cancan.common.crud.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ProjetoRepository extends SuperRepository<Projeto> {

    @Query("""
                select count(p) > 0
                from Projeto p
                join p.membros m
                where p.id = :projetoId
                  and m.id = :usuarioId
            """)
    boolean usuarioParticipaDoProjeto(
            @Param("projetoId") UUID projetoId,
            @Param("usuarioId") UUID usuarioId
    );
}
