package com.articos.cancan.common;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@MappedSuperclass
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public abstract class SuperEntity<PAYLOAD_DTO, RESPONSE_DTO, LIST_DTO> implements DtoConvertible<PAYLOAD_DTO, RESPONSE_DTO, LIST_DTO>, Descritivo {
    @Id
    private UUID id;

    @Version
    @Column(nullable = false)
    private Integer version;

    public SuperEntity(SuperPayloadResponseDTO dto) {
        if (dto.getId() != null) {
            this.id = dto.getId();
            this.version = dto.getVersion();
        }
    }

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    public void initialize() {
    }
}
