package com.articos.cancan.domain.tarefa.dto;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.tarefa.*;
import com.articos.cancan.domain.tarefa.prioridadetarefa.*;
import com.articos.cancan.domain.tarefa.statustarefa.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.*;
import java.util.*;

@NoArgsConstructor
@Getter
public class TarefaPayloadDTO extends SuperPayloadDTO<Tarefa> {
    @NotBlank(message = "Título é obrigatório.")
    @Size(max = 30, message = "Título deve ter no máximo 30 caracteres.")
    private String titulo;

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres.")
    private String descricao;

    @NotNull(message = "Status é obrigatório.")
    private StatusTarefa status;

    @NotNull(message = "Prioridade é obrigatória.")
    private PrioridadeTarefa prioridade;

    @NotNull(message = "Responsável é obrigatório.")
    private UUID responsavel;

    @NotNull(message = "Projeto é obrigatório.")
    private UUID projeto;

    @NotNull(message = "Prazo é obrigatório.")
    private LocalDate prazo;

    public TarefaPayloadDTO(Tarefa tarefa) {
        super(tarefa);
        this.titulo = tarefa.getTitulo();
        this.descricao = tarefa.getDescricao();
        this.status = tarefa.getStatus();
        this.prioridade = tarefa.getPrioridade();
        this.responsavel = tarefa.getResponsavel().getId();
        this.projeto = tarefa.getProjeto().getId();
        this.prazo = tarefa.getPrazo();

    }

    @Override
    public Tarefa toEntity() {
        return new Tarefa(this);
    }
}
