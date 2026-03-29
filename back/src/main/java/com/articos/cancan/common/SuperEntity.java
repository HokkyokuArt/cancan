package com.articos.cancan.common;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@MappedSuperclass
@Getter
@NoArgsConstructor
public abstract class SuperEntity<DTO> implements DtoConvertible<DTO>, Descritivo {
    @Id
    private UUID id;

    @Version
    @Column(nullable = false)
    private Integer version;

    public SuperEntity(SuperPaylodResponeDTO dto) {
        this.id = dto.getId();
        this.version = dto.getVersion();
    }

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
