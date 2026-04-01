package com.articos.cancan.domain.projeto.dto;

import com.articos.cancan.common.*;
import com.articos.cancan.common.crud.*;
import com.articos.cancan.domain.projeto.*;
import lombok.*;

@Getter
public class ProjetoListDTO extends AbstractEntityDTO {

    private String sigla;
    private String nome;
    private String dono;

    public ProjetoListDTO(Projeto entity) {
        super(entity);
        this.sigla = entity.getSigla();
        this.nome = entity.getNome();
        this.dono = entity.getDono().getDescritivo();
    }
}
