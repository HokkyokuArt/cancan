package com.articos.cancan.domain.projeto;

import com.articos.cancan.common.annotations.*;
import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.projeto.dto.*;
import com.articos.cancan.domain.usuario.*;
import com.articos.cancan.utils.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "PROJETO")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"nome"}, callSuper = false)
@EntidadeFrontFriendly(nome = "Projeto", artigo = Artigo.MASCULINO)
public class Projeto extends SuperEntity<ProjetoPayloadDTO, ProjetoPayloadDTO, ProjetoListResponsavelDTO> {
    @Column(nullable = false, length = 30)
    private String nome;

    @Column(nullable = false, length = 3)
    private String sigla;

    @Column(nullable = false, length = 100)
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
        this.nome = dto.getNome().toUpperCase();
        this.sigla = dto.getSigla().toUpperCase();
        this.descricao = dto.getDescricao();
        this.dono = dono;
        this.membros = membros;
    }

    @Override
    public String getDescritivo() {
        return this.nome;
    }
}
