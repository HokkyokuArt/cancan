package com.articos.cancan.common;

import lombok.*;

import java.util.*;

@Getter
public class AbstractEntityDTO implements Descritivo {
    UUID id;
    String descritivo;

    public AbstractEntityDTO(SuperEntity entity) {
        this.id = entity.getId();
        this.descritivo = entity.getDescritivo();
    }
}
