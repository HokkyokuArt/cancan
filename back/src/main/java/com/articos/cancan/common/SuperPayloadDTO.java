package com.articos.cancan.common;

import lombok.*;

@NoArgsConstructor
public abstract class SuperPayloadDTO<T extends SuperEntity> extends SuperResponseDTO<T> implements EntityConvertible<T> {
    public SuperPayloadDTO(T entity) {
        super(entity);
    }
}
