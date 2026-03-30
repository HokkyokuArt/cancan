package com.articos.cancan.domain.projeto;

import com.articos.cancan.common.*;
import com.articos.cancan.domain.projeto.dto.*;
import com.articos.cancan.domain.usuario.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.*;

import java.util.*;

@Entity
@Table(name = "PROJETO")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"nome"}, callSuper = false)
public class Projeto extends SuperEntity<ProjetoPayloadDTO, ProjetoPayloadDTO, AbstractEntityDTO> {
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 3)
    private String sigla;

    @Column(nullable = false, length = 500)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DONO_FK", nullable = false)
    private Usuario dono;

    @ManyToMany
    @JoinTable(
            name = "PROJETO_MEMBROS",
            joinColumns = @JoinColumn(name = "PROJETO_FK"),
            inverseJoinColumns = @JoinColumn(name = "MEMBRO_FK")
    )
    private Set<Usuario> membros = new HashSet<>();

    public Projeto(ProjetoPayloadDTO dto) {
        super(dto);
    }

    public void setValues(ProjetoPayloadDTO dto, Usuario dono, Set<Usuario> membros) {
        this.nome = dto.getNome();
        this.sigla = dto.getSigla();
        this.descricao = dto.getDescricao();
        this.dono = dono;
        this.membros = membros;
    }

    public void initialize() {
        Hibernate.initialize(this.membros);
    }

    @Override
    public void setValues(ProjetoPayloadDTO o) {
    }

    @Override
    public ProjetoPayloadDTO toDTO() {
        return new ProjetoPayloadDTO(this);
    }


    @Override
    public AbstractEntityDTO toListDTO() {
        return new AbstractEntityDTO(this);
    }

    @Override
    public String getDescritivo() {
        return this.nome;
    }
}
