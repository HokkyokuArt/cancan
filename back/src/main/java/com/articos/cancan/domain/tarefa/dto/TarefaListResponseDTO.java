package com.articos.cancan.domain.tarefa.dto;

import com.articos.cancan.common.*;
import com.articos.cancan.domain.tarefa.*;
import com.articos.cancan.utils.*;
import lombok.*;

import java.time.*;

@Getter
public class TarefaListResponseDTO extends AbstractEntityDTO {
    private final String titulo;
    private final String codigo;
    private final String status;
    private final String prioridade;
    private final String responsavel;
    private final String prazo;
    private final String dataCriacao;

    public TarefaListResponseDTO(Tarefa entity) {
        super(entity);
        this.titulo = entity.getTitulo();
        this.codigo = entity.getCodigo();
        this.status = entity.getStatus().getDescritivo();
        this.prioridade = entity.getPrioridade().getDescritivo();
        this.responsavel = entity.getResponsavel().getDescritivo();
        this.prazo = DateUtils.displayPattern(entity.getPrazo());
        this.dataCriacao = DateUtils.displayPattern(entity.getDataCriacao().toLocalDate());
    }
}
