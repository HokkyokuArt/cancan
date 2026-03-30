package com.articos.cancan.domain.tarefa.dto;

import com.articos.cancan.common.*;
import com.articos.cancan.domain.tarefa.*;
import com.articos.cancan.domain.tarefa.prioridadetarefa.*;
import com.articos.cancan.domain.tarefa.statustarefa.*;
import lombok.*;

import java.time.*;
import java.util.*;

@NoArgsConstructor
@Getter
public class TarefaResponseDTO extends SuperResponseDTO<Tarefa> {
    private String titulo;
    private String codigo;
    private String descricao;
    private StatusTarefa status;
    private PrioridadeTarefa prioridade;
    private UUID responsavel;
    private UUID projeto;
    private LocalDateTime dataCriacao;
    private LocalDateTime ultimaAtualizacao;
    private LocalDate prazo;

    public TarefaResponseDTO(Tarefa entity) {
        super(entity);
        this.titulo = entity.getTitulo();
        this.codigo = entity.getCodigo();
        this.descricao = entity.getDescricao();
        this.status = entity.getStatus();
        this.prioridade = entity.getPrioridade();
        this.responsavel = entity.getResponsavel().getId();
        this.projeto = entity.getProjeto().getId();
        this.dataCriacao = entity.getDataCriacao();
        this.ultimaAtualizacao = entity.getUltimaAtualizacao();
        this.prazo = entity.getPrazo();
    }
}
