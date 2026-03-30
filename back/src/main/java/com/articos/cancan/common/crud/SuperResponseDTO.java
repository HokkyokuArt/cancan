package com.articos.cancan.common.crud;

import lombok.*;

import java.util.*;

@Getter
@NoArgsConstructor
public abstract class SuperResponseDTO<T extends SuperEntity> {
    private UUID id;
    private Integer version;

    public SuperResponseDTO(T entity) {
        this.id = entity.getId();
        this.version = entity.getVersion();
    }
}
