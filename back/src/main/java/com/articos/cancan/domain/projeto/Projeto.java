package com.articos.cancan.domain.projeto;

import com.articos.cancan.common.*;
import com.articos.cancan.domain.projeto.dto.*;
import com.articos.cancan.domain.usuario.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "PROJETO")
@Getter
@Setter
@NoArgsConstructor
public class Projeto extends SuperEntity<ProjetoPayloadDTO> {
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 500)
    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DONO_FK", nullable = false)
    private Usuario dono;

    @ManyToMany
    @JoinTable(
            name = "PROJETO_MEMBROS",
            joinColumns = @JoinColumn(name = "PROJETO_FK"),
            inverseJoinColumns = @JoinColumn(name = "MEMBRO_FK")
    )
    private Set<Usuario> membros = new HashSet<>();

    public Projeto(ProjetoPayloadDTO dto, Usuario dono, Set<Usuario> membros) {
        super(dto);
        this.nome = dto.getNome();
        this.descricao = dto.getDescricao();
        this.dono = dono;
        this.membros = membros;
    }

    @Override
    public ProjetoPayloadDTO toDTO() {
        return new ProjetoPayloadDTO(this);
    }

    @Override
    public String getDescritivo() {
        return this.nome;
    }
}
