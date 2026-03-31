package com.articos.cancan.utils;

import com.articos.cancan.common.annotations.*;

public final class EntidadeFrontFriendlyUtils {
    public static String getNome(Class<?> entityClass) {
        EntidadeFrontFriendly annotation = getAnnotation(entityClass);
        return annotation != null ? annotation.nome() : entityClass.getSimpleName();
    }

    public static Artigo getArtigo(Class<?> entityClass) {
        EntidadeFrontFriendly annotation = getAnnotation(entityClass);
        return annotation != null ? annotation.artigo() : Artigo.FEMININO;
    }

    private static EntidadeFrontFriendly getAnnotation(Class<?> entityClass) {
        return entityClass.getAnnotation(EntidadeFrontFriendly.class);
    }
}
