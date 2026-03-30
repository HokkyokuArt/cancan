package com.articos.cancan.domain.tarefa.statustarefa;

import com.articos.cancan.common.*;
import lombok.*;

@RequiredArgsConstructor
@Getter
public enum StatusTarefa implements EnumAttributeConverterDescritivo {
    TODO("T", "TODO"),
    IN_PROGRESS("P", "IN PROGRESS"),
    DONE("D", "DONE");

    private final String codigo;
    private final String descritivo;
}
