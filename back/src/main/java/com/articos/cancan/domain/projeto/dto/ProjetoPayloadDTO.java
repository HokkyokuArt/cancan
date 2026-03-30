package com.articos.cancan.domain.projeto.dto;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.projeto.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.*;
import java.util.stream.*;

@NoArgsConstructor
@Getter
public class ProjetoPayloadDTO extends SuperPayloadDTO<Projeto> {
    @NotEmpty
    private String nome;
    @NotEmpty
    private String sigla;
    @NotEmpty
    private String descricao;
    @NotNull
    private UUID dono;
    private Set<UUID> membros = new HashSet<>();

    public ProjetoPayloadDTO(Projeto projeto) {
        super(projeto);
        this.nome = projeto.getNome();
        this.sigla = projeto.getSigla();
        this.descricao = projeto.getDescricao();
        this.dono = projeto.getDono().getId();
        this.membros = projeto.getMembros().stream()
                .map(SuperEntity::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public Projeto toEntity() {
        return new Projeto(this);
    }
}
