package com.articos.cancan.common;

public abstract class SuperValidator<T extends SuperEntity> {
    protected void validate(T entity) throws Exception {
    }

    protected void validateExcluir(T entity) throws Exception {
    }
}
