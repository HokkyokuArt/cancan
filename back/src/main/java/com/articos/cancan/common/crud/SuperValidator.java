package com.articos.cancan.common.crud;

public abstract class SuperValidator<T extends SuperEntity, PAYLOAD_DTO extends SuperPayloadDTO<T>> {
    protected void validateCreate(T entity, PAYLOAD_DTO dto) {
    }

    protected void validateUpdate(T entityAntiga, PAYLOAD_DTO dto) {
    }

    protected void validateView(T entity) {

    }

    protected void validateExcluir(T entity) {
    }
}
