package com.articos.cancan.domain.projeto.dto;

import com.articos.cancan.common.*;
import com.articos.cancan.domain.projeto.*;
import lombok.*;

@Getter
public class ProjetoListResponsavelDTO extends AbstractEntityDTO {

    private final String sigla;
    private final String nome;
    private final String dono;

    public ProjetoListResponsavelDTO(Projeto entity) {
        super(entity);
        this.sigla = entity.getSigla();
        this.nome = entity.getNome();
        this.dono = entity.getDono().getDescritivo();
    }
}
