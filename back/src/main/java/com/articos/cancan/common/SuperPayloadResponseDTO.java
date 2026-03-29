package com.articos.cancan.common;

import lombok.*;

import java.util.*;

@Getter
@NoArgsConstructor
public abstract class SuperPayloadResponseDTO<T extends SuperEntity> implements EntityConvertible<T> {
    private UUID id;
    private Integer version;

    public SuperPayloadResponseDTO(T entity) {
        this.id = entity.getId();
        this.version = entity.getVersion();
    }
}
