package com.articos.cancan.domain.projeto.dto;

import com.articos.cancan.common.*;
import com.articos.cancan.domain.projeto.*;
import lombok.*;

import java.util.*;
import java.util.stream.*;

@NoArgsConstructor
@Getter
public class ProjetoPayloadDTO extends SuperPaylodResponeDTO<Projeto> {
    private String nome;
    private String descricao;
    private UUID dono;
    private Set<UUID> membros;

    public ProjetoPayloadDTO(Projeto projeto) {
        super(projeto);
        this.nome = projeto.getNome();
        this.descricao = projeto.getDescricao();
        this.dono = projeto.getDono().getId();
        this.membros = projeto.getMembros().stream()
                .map(SuperEntity::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public Projeto toEntity() {
        return null;
    }
}
