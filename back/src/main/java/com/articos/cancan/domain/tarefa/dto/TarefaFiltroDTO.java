package com.articos.cancan.domain.tarefa.dto;

import com.articos.cancan.common.interfaces.*;
import com.articos.cancan.domain.tarefa.*;
import com.articos.cancan.domain.tarefa.prioridadetarefa.*;
import com.articos.cancan.domain.tarefa.statustarefa.*;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.jpa.domain.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
public class TarefaFiltroDTO implements Filtro<Tarefa> {
    private StatusTarefa status;
    private PrioridadeTarefa prioridade;
    private UUID responsavel;
    private List<LocalDate> datas;

    @Override
    public Specification<Tarefa> buildSpecification() {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            if (prioridade != null) {
                predicates.add(criteriaBuilder.equal(root.get("prioridade"), prioridade));
            }

            if (responsavel != null) {
                predicates.add(criteriaBuilder.equal(root.get("responsavel").get("id"), responsavel));
            }

            if (datas != null && !datas.isEmpty()) {
                predicates.add(root.get("dataCriacao").in(datas));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
