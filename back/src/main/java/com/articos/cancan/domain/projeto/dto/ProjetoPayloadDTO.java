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
    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 30, message = "Nome deve ter no máximo 30 caracteres.")
    private String nome;
    @NotBlank(message = "Sigla é obrigatória.")
    @Size(min = 3, max = 3, message = "Sigla deve ter exatamente 3 caracteres.")
    private String sigla;
    @NotBlank(message = "Descrição é obrigatória.")
    @Size(max = 100, message = "Descrição deve ter no máximo 100 caracteres.")
    private String descricao;
    @NotNull(message = "Dono é obrigatório.")
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
