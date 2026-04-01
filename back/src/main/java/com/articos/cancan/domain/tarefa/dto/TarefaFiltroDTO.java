package com.articos.cancan.domain.tarefa.dto;

import com.articos.cancan.common.crud.*;
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
public class TarefaFiltroDTO extends SuperFiltroDTO<Tarefa> {
    private StatusTarefa status;
    private PrioridadeTarefa prioridade;
    private UUID responsavel;
    private LocalDate criacaoInicio;
    private LocalDate criacaoFim;
    private LocalDate prazoInicio;
    private LocalDate prazoFim;
    private Boolean isAdmin;
    private UUID usuarioAtual;

    @Override
    public Specification<Tarefa> buildSpecification() {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (this.busca != null && !this.busca.isBlank()) {
                String termo = "%" + this.busca.toLowerCase().trim() + "%";

                Predicate tituloLike = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("titulo")),
                        termo
                );

                Predicate descricaoLike = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("descricao")),
                        termo
                );

                predicates.add(criteriaBuilder.or(tituloLike, descricaoLike));
            }

            if (this.status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), this.status));
            }

            if (this.prioridade != null) {
                predicates.add(criteriaBuilder.equal(root.get("prioridade"), this.prioridade));
            }

            if (this.responsavel != null) {
                predicates.add(criteriaBuilder.equal(root.get("responsavel").get("id"), this.responsavel));
            }

            predicateRangeDate(this.criacaoInicio, this.criacaoFim, root.get("dataCriacao"), criteriaBuilder, predicates);
            predicateRangeDate(this.prazoInicio, this.prazoFim, root.get("prazo"), criteriaBuilder, predicates);

            if (!Boolean.TRUE.equals(this.isAdmin) && this.usuarioAtual != null) {
                query.distinct(true);
                Join<Object, Object> projetoJoin = root.join("projeto");
                Join<Object, Object> membrosJoin = projetoJoin.join("membros");
                predicates.add(criteriaBuilder.equal(membrosJoin.get("id"), this.usuarioAtual));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void predicateRangeDate(LocalDate inicio, LocalDate fim, Path<LocalDate> path, CriteriaBuilder criteriaBuilder,
                                           ArrayList<Predicate> predicates) {
        if (inicio != null && fim != null) {
            predicates.add(
                    criteriaBuilder.between(
                            path,
                            inicio,
                            fim
                    )
            );
        } else if (inicio != null) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(
                            path,
                            inicio
                    )
            );
        } else if (fim != null) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(
                            path,
                            fim
                    )
            );
        }
    }
}
