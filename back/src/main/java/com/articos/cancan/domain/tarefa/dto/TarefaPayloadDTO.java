package com.articos.cancan.domain.tarefa.dto;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.projeto.*;
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

    @NotEmpty
    private String titulo;
    @NotEmpty
    private String descricao;
    @NotNull
    private StatusTarefa status;
    @NotNull
    private PrioridadeTarefa prioridade;
    @NotNull
    private UUID responsavel;
    @NotNull
    private UUID projeto;
    @NotNull
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
