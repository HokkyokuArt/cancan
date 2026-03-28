package com.articos.cancan.common;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@MappedSuperclass
public abstract class SuperEntity {
    @Id
    private UUID id;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    public UUID getId() {
        return this.id;
    }
}
