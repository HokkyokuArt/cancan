package com.articos.cancan.common;

import com.articos.cancan.common.crud.*;
import com.articos.cancan.common.interfaces.*;
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
