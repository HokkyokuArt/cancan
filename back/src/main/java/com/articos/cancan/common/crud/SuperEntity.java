package com.articos.cancan.common.crud;

import com.articos.cancan.common.*;
import com.articos.cancan.common.interfaces.*;
import com.articos.cancan.utils.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@MappedSuperclass
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public abstract class SuperEntity<
        PAYLOAD_DTO,
        RESPONSE_DTO,
        LIST_DTO extends AbstractEntityDTO
        >
        implements DtoConvertible<PAYLOAD_DTO, RESPONSE_DTO, LIST_DTO>, Descritivo {
    @Id
    private UUID id;

    @Version
    @Column(nullable = false)
    private Integer version;

    public SuperEntity(SuperPayloadDTO dto) {
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

    @Override
    public PAYLOAD_DTO toPayloadDTO() {
        Class<PAYLOAD_DTO> responseDtoClass =
                (Class<PAYLOAD_DTO>) ReflectionUtils.inferGenericType(this.getClass(), 0);
        return ReflectionUtils.newInstance(this, responseDtoClass);
    }

    @Override
    public RESPONSE_DTO toResponseDTO() {
        Class<RESPONSE_DTO> responseDtoClass =
                (Class<RESPONSE_DTO>) ReflectionUtils.inferGenericType(this.getClass(), 1);
        return ReflectionUtils.newInstance(this, responseDtoClass);
    }

    @Override
    public LIST_DTO toListDTO() {
        Class<LIST_DTO> responseDtoClass = (Class<LIST_DTO>) ReflectionUtils.inferGenericType(this.getClass(), 2);
        return ReflectionUtils.newInstance(this, responseDtoClass);
    }
}
