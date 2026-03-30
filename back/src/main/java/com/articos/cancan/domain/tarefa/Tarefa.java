package com.articos.cancan.domain.tarefa;

import com.articos.cancan.common.*;
import com.articos.cancan.domain.projeto.*;
import com.articos.cancan.domain.tarefa.dto.*;
import com.articos.cancan.domain.tarefa.prioridadetarefa.*;
import com.articos.cancan.domain.tarefa.statustarefa.*;
import com.articos.cancan.domain.usuario.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Entity
@Table(name = "TAREFA")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"codigo"}, callSuper = false)
public class Tarefa extends SuperEntity<TarefaPayloadDTO, TarefaPayloadDTO, AbstractEntityDTO> {

    @Column(nullable = false, length = 300)
    private String titulo;

    @Column(nullable = false, length = 10)
    private String codigo;

    @Column(nullable = false, length = 1000)
    private String descricao;

    @Convert(converter = StatusTarefaAttributeConverter.class)
    @Column(nullable = false, length = 1)
    private StatusTarefa status;

    @Convert(converter = PrioridadeTarefaAttributeConverter.class)
    @Column(nullable = false, length = 1)
    private PrioridadeTarefa prioridadeTarefa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RESPONSAVEL_FK", nullable = false)
    private Usuario responsavel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PROJETO_FK", nullable = false)
    private Projeto projeto;

    @Column(name = "DATA_CRIACAO", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "ULTIMA_ATUALIZACAO", nullable = false)
    private LocalDateTime ultimaAtualizacao;

    @Override
    public void prePersist() {
        super.prePersist();
        LocalDateTime agora = LocalDateTime.now();
        this.dataCriacao = agora;
        this.ultimaAtualizacao = agora;
    }

    @PreUpdate
    public void preUpdate() {
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    @Override
    public String getDescritivo() {
        return null;
    }

    @Override
    public void setValues(TarefaPayloadDTO tarefaPayloadDTO) {
    }

    @Override
    public TarefaPayloadDTO toDTO() {
        return null;
    }

    @Override
    public AbstractEntityDTO toListDTO() {
        return null;
    }
}
