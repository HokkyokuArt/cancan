package com.articos.cancan.domain.projeto.dto;

import com.articos.cancan.common.*;
import com.articos.cancan.domain.projeto.*;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.jpa.domain.*;

import java.util.*;

@Getter
@Setter
public class ProjetoFiltroDTO implements Filtro<Projeto> {
    private String nome;

    public Specification<Projeto> buildSpecification() {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (this.nome != null) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("nome")),
                                "%" + this.nome.toLowerCase() + "%"
                        )
                );
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
