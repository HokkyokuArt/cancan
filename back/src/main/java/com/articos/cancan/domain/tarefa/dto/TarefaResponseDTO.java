package com.articos.cancan.domain.tarefa.dto;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.tarefa.*;
import com.articos.cancan.utils.*;
import lombok.*;

@NoArgsConstructor
@Getter
public class TarefaResponseDTO extends SuperResponseDTO<Tarefa> {
    private String titulo;
    private String codigo;
    private String descricao;
    private String status;
    private String prioridade;
    private String responsavel;
    private String projeto;
    private String dataCriacao;
    private String ultimaAtualizacao;
    private String prazo;

    public TarefaResponseDTO(Tarefa entity) {
        super(entity);
        this.titulo = entity.getTitulo();
        this.codigo = entity.getCodigo();
        this.descricao = entity.getDescricao();
        this.status = entity.getStatus().getDescritivo();
        this.prioridade = entity.getPrioridade().getDescritivo();
        this.responsavel = entity.getResponsavel().getDescritivo();
        this.projeto = entity.getProjeto().getDescritivo();
        this.dataCriacao = DateUtils.displayPattern(entity.getDataCriacao().toLocalDate());
        this.ultimaAtualizacao = DateUtils.displayPattern(entity.getUltimaAtualizacao().toLocalDate());
        this.prazo = DateUtils.displayPattern(entity.getPrazo());
    }
}
