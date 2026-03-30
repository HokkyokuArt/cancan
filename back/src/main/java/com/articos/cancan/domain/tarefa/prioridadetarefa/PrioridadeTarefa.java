package com.articos.cancan.domain.tarefa.prioridadetarefa;

import com.articos.cancan.common.interfaces.*;
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

    public boolean isLow() {
        return LOW.equals(this);
    }

    public boolean isMedium() {
        return MEDIUM.equals(this);
    }

    public boolean isHigh() {
        return HIGH.equals(this);
    }

    public boolean isCritical() {
        return CRITICAL.equals(this);
    }
}
