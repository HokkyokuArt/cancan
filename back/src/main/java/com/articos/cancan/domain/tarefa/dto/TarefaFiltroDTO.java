package com.articos.cancan.domain.tarefa.dto;

import com.articos.cancan.common.interfaces.*;
import com.articos.cancan.domain.tarefa.*;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.jpa.domain.*;

import java.util.*;

@Getter
@Setter
public class TarefaFiltroDTO implements Filtro<Tarefa> {
    @Override
    public Specification<Tarefa> buildSpecification() {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
