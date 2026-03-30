package com.articos.cancan.domain.tarefa.prioridadetarefa;

import com.articos.cancan.common.*;
import lombok.*;

@RequiredArgsConstructor
@Getter
public enum PrioridadeTarefa implements EnumAttributeConverterDescritivo {
    LOW("L", "LOW"),
    MEDIUM("M", "MEDIUM"),
    HIGH("H", "HIGH"),
    CRITICAL("C", "CRITICAL");

    private final String codigo;
    private final String descritivo;
}
