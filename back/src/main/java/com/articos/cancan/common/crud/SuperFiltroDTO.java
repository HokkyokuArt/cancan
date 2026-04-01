package com.articos.cancan.common.crud;

import com.articos.cancan.common.interfaces.*;
import lombok.*;

@Getter
@Setter
public abstract class SuperFiltroDTO<T extends SuperEntity> implements Filtro<T> {
    protected String buscaSimples;
    protected String busca;
}
