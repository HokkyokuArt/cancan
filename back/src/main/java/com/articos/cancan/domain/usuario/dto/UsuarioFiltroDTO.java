package com.articos.cancan.domain.usuario.dto;

import com.articos.cancan.common.interfaces.*;
import com.articos.cancan.domain.usuario.*;
import com.articos.cancan.security.jwt.role.*;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.jpa.domain.*;
import org.springframework.util.*;

import java.util.*;

@Getter
@Setter
public class UsuarioFiltroDTO implements Filtro<Usuario> {
    private String busca;
    private List<Role> roles;

    public Specification<Usuario> buildSpecification() {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (this.busca != null) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("nome")),
                                "%" + this.busca.toLowerCase() + "%"
                        )
                );
            }

            if (!CollectionUtils.isEmpty(this.roles)) {
                predicates.add(root.get("role").in(this.roles));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
