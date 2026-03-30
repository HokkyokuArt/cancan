package com.articos.cancan.common.crud;

public abstract class SuperValidator<T extends SuperEntity, PAYLOAD_DTO extends SuperPayloadDTO<T>> {
    protected void validateCreate(T entity, PAYLOAD_DTO dto) throws Exception {
    }

    protected void validateUpdate(T entity, PAYLOAD_DTO dto) throws Exception {
    }

    protected void validateExcluir(T entity) throws Exception {
    }
}
