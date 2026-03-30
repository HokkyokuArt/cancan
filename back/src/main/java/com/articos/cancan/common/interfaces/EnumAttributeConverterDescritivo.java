package com.articos.cancan.common.interfaces;

public interface EnumAttributeConverterDescritivo extends Descritivo {
    String getCodigo();

    static <E extends Enum<E> & EnumAttributeConverterDescritivo> E ofCodigo(Class<E> enumClass, String codigo) {
        for (E constante : enumClass.getEnumConstants()) {
            if (constante.getCodigo().equals(codigo)) {
                return constante;
            }
        }
        throw new IllegalArgumentException(
                "Código inválido '" + codigo + "' para o enum " + enumClass.getSimpleName()
        );
    }
}
