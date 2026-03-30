package com.articos.cancan.common;

import com.articos.cancan.common.interfaces.*;
import com.articos.cancan.utils.*;
import jakarta.persistence.*;

public abstract class SuperAttributeConverter<T extends Enum<T> & EnumAttributeConverterDescritivo> implements AttributeConverter<T, String> {

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getCodigo() : null;
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        Class<?> enumClass = ReflectionUtils.inferGenericType(this.getClass(), 0);
        return dbData != null
                ? EnumAttributeConverterDescritivo.ofCodigo((Class<T>) enumClass, dbData)
                : null;
    }
}
